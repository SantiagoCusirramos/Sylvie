package com.sylvie.recomendaciones.controller;

import com.sylvie.recomendaciones.dto.RecomendacionRequestDTO;
import com.sylvie.recomendaciones.dto.RecomendacionResponseDTO;
import com.sylvie.recomendaciones.entity.Recomendacion;
import com.sylvie.recomendaciones.service.RecomendacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recomendaciones")
public class RecomendacionController {

    private final RecomendacionService recomendacionService;

    public RecomendacionController(RecomendacionService recomendacionService) {
        this.recomendacionService = recomendacionService;
    }

    // POST /api/recomendaciones/generar
    // Body: { "usuarioId": 1, "codigoBarras": "7501234567890" }
    @PostMapping("/generar")
    public ResponseEntity<?> generarRecomendacion(@RequestBody RecomendacionRequestDTO request) {
        try {
            RecomendacionResponseDTO response = recomendacionService.generarRecomendacion(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /api/recomendaciones/historial/{usuarioId}
    @GetMapping("/historial/{usuarioId}")
    public ResponseEntity<?> obtenerHistorial(@PathVariable Long usuarioId) {
        try {
            List<Recomendacion> historial = recomendacionService.obtenerHistorial(usuarioId);
            return ResponseEntity.ok(historial);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /api/recomendaciones/health
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("ms-recomendaciones funcionando correctamente");
    }
}