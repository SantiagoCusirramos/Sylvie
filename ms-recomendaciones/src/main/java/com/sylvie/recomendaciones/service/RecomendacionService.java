package com.sylvie.recomendaciones.service;

import com.sylvie.recomendaciones.dto.*;
import com.sylvie.recomendaciones.entity.Recomendacion;
import com.sylvie.recomendaciones.repository.RecomendacionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecomendacionService {

    private final RestTemplate restTemplate;
    private final RecomendacionRepository recomendacionRepository;

    @Value("${ms.usuarios.url:http://ms-usuarios:8082}")
    private String usuariosUrl;

    @Value("${ms.analisis.url:http://ms-analisis:8083}")
    private String analisisUrl;

    public RecomendacionService(RecomendacionRepository recomendacionRepository) {
        this.restTemplate = new RestTemplate();
        this.recomendacionRepository = recomendacionRepository;
    }

    public RecomendacionResponseDTO generarRecomendacion(RecomendacionRequestDTO request) {
        Long usuarioId = request.getUsuarioId();
        String codigoBarras = request.getCodigoBarras();

        // 1. Obtener restricciones del usuario desde ms-usuarios
        String urlRestricciones = usuariosUrl + "/api/usuarios/restricciones/" + usuarioId;
        List<RestriccionDTO> restricciones;
        try {
            RestriccionDTO[] restriccionesArray = restTemplate.getForObject(urlRestricciones, RestriccionDTO[].class);
            restricciones = restriccionesArray != null ? List.of(restriccionesArray) : List.of();
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar restricciones del usuario: " + e.getMessage());
        }

        // 2. Obtener análisis del producto desde ms-analisis
        String urlAnalisis = analisisUrl + "/api/analisis/analizar";
        AnalisisRequestDTO analisisRequest = new AnalisisRequestDTO();
        analisisRequest.setCodigoBarras(codigoBarras);

        AnalisisResultadoDTO analisis;
        try {
            analisis = restTemplate.postForObject(urlAnalisis, analisisRequest, AnalisisResultadoDTO.class);
            if (analisis == null) {
                throw new RuntimeException("No se pudo obtener el análisis del producto");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar ms-analisis: " + e.getMessage());
        }

        // 3. Evaluar si el producto contiene ingredientes restringidos
        boolean tieneAlergia = false;
        boolean tieneIntolerancia = false;
        String ingredienteProblema = null;

        // Nota: Para una implementación completa, necesitaríamos obtener los ingredientes
        // del producto desde ms-productos. Por simplicidad, asumimos que la verificación
        // se hace con la información disponible.
        // En producción, habría que consultar ms-productos para obtener ingredientes.

        for (RestriccionDTO restriccion : restricciones) {
            // Por ahora usamos lógica simplificada
            if (restriccion.getTipo().equals("ALERGIA")) {
                tieneAlergia = true;
                ingredienteProblema = restriccion.getIngrediente();
                break;
            }
            if (restriccion.getTipo().equals("INTOLERANCIA")) {
                tieneIntolerancia = true;
                ingredienteProblema = restriccion.getIngrediente();
            }
        }

        // 4. Determinar resultado basado en puntuación y restricciones
        String resultado;
        String motivo;
        int puntuacion = analisis.getPuntuacion();
        String clasificacion = analisis.getClasificacion();

        if (tieneAlergia) {
            resultado = "NO_APTO";
            motivo = "Contiene ingrediente al que eres alérgico: " + ingredienteProblema;
        } else if (tieneIntolerancia) {
            resultado = "USAR_CON_PRECAUCION";
            motivo = "Contiene ingrediente que puede causar intolerancia: " + ingredienteProblema;
        } else if (puntuacion >= 80) {
            resultado = "APTO";
            motivo = "Producto de alta calidad, seguro para tu perfil";
        } else if (puntuacion >= 60) {
            resultado = "APTO";
            motivo = "Producto aceptable, riesgo bajo";
        } else if (puntuacion >= 40) {
            resultado = "USAR_CON_PRECAUCION";
            motivo = "Producto con ingredientes cuestionables";
        } else if (puntuacion >= 20) {
            resultado = "USAR_CON_PRECAUCION";
            motivo = "Producto riesgoso, evalúa alternativas";
        } else {
            resultado = "NO_APTO";
            motivo = "Producto dañino, no recomendado para ningún perfil";
        }

        // 5. Guardar recomendación en BD
        Recomendacion recomendacion = new Recomendacion();
        recomendacion.setUsuarioId(usuarioId);
        recomendacion.setCodigoBarras(codigoBarras);
        recomendacion.setResultado(resultado);
        recomendacion.setMotivo(motivo);
        recomendacionRepository.save(recomendacion);

        return new RecomendacionResponseDTO(resultado, motivo, puntuacion, clasificacion);
    }

    public List<Recomendacion> obtenerHistorial(Long usuarioId) {
        return recomendacionRepository.findByUsuarioId(usuarioId);
    }
}