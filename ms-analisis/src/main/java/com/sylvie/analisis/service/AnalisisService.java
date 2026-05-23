package com.sylvie.analisis.service;

import com.sylvie.analisis.dto.AnalisisRequestDTO;
import com.sylvie.analisis.dto.AnalisisResponseDTO;
import com.sylvie.analisis.dto.IngredienteDTO;
import com.sylvie.analisis.dto.ProductoDTO;
import com.sylvie.analisis.entity.Analisis;
import com.sylvie.analisis.repository.AnalisisRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AnalisisService {

    private final RestTemplate restTemplate;
    private final AnalisisRepository analisisRepository;

    @Value("${ms.productos.url:http://ms-productos:8081}")
    private String productosUrl;

    public AnalisisService(AnalisisRepository analisisRepository) {
        this.restTemplate = new RestTemplate();
        this.analisisRepository = analisisRepository;
    }

    public AnalisisResponseDTO analizarProducto(AnalisisRequestDTO request) {
        String codigoBarras = request.getCodigoBarras();

        // 1. Consultar a ms-productos para obtener el producto y sus ingredientes
        String url = productosUrl + "/api/productos/barras/" + codigoBarras;
        ProductoDTO producto;

        try {
            producto = restTemplate.getForObject(url, ProductoDTO.class);
            if (producto == null) {
                throw new RuntimeException("Producto no encontrado");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar ms-productos: " + e.getMessage());
        }

        // 2. Calcular puntuación basada en nivel de riesgo de ingredientes
        int puntuacion = calcularPuntuacion(producto.getIngredientes());

        // 3. Determinar clasificación
        String clasificacion = determinarClasificacion(puntuacion);
        String mensaje = generarMensaje(clasificacion);

        // 4. Guardar análisis en BD
        Analisis analisis = new Analisis();
        analisis.setCodigoBarras(codigoBarras);
        analisis.setPuntuacion(puntuacion);
        analisis.setClasificacion(clasificacion);
        analisis.setDetalle(mensaje);
        analisisRepository.save(analisis);

        return new AnalisisResponseDTO(codigoBarras, puntuacion, clasificacion, mensaje);
    }

    private int calcularPuntuacion(java.util.List<IngredienteDTO> ingredientes) {
        if (ingredientes == null || ingredientes.isEmpty()) {
            return 50; // Puntuación neutral si no hay ingredientes
        }

        int puntuacion = 100;
        int penalizacionAlto = 0;
        int penalizacionMedio = 0;

        for (IngredienteDTO ing : ingredientes) {
            String riesgo = ing.getNivelRiesgo();
            if (riesgo == null) continue;

            switch (riesgo.toUpperCase()) {
                case "ALTO":
                    penalizacionAlto += 25;
                    break;
                case "MEDIO":
                    penalizacionMedio += 10;
                    break;
                // BAJO no resta
            }
        }

        puntuacion = puntuacion - penalizacionAlto - penalizacionMedio;
        return Math.max(0, Math.min(100, puntuacion));
    }

    private String determinarClasificacion(int puntuacion) {
        if (puntuacion >= 80) return "BUENO";
        if (puntuacion >= 60) return "RIESGO_BAJO";
        if (puntuacion >= 40) return "REGULAR";
        if (puntuacion >= 20) return "RIESGO_ALTO";
        return "MALO";
    }

    private String generarMensaje(String clasificacion) {
        switch (clasificacion) {
            case "BUENO":
                return "Producto seguro, bajo riesgo para la salud";
            case "RIESGO_BAJO":
                return "Producto aceptable, precaución mínima";
            case "REGULAR":
                return "Producto con ingredientes cuestionables";
            case "RIESGO_ALTO":
                return "Producto riesgoso, evitar su uso frecuente";
            case "MALO":
                return "Producto dañino, no recomendado";
            default:
                return "Análisis completado";
        }
    }
}