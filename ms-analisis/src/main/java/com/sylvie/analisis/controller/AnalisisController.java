package com.sylvie.analisis.controller;

import com.sylvie.analisis.dto.AnalisisRequestDTO;
import com.sylvie.analisis.dto.AnalisisResponseDTO;
import com.sylvie.analisis.service.AnalisisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analisis")
public class AnalisisController {

    private final AnalisisService analisisService;

    public AnalisisController(AnalisisService analisisService) {
        this.analisisService = analisisService;
    }

    // POST /api/analisis/analizar
    // Body: { "codigoBarras": "7501234567890" }
    @PostMapping("/analizar")
    public ResponseEntity<?> analizarProducto(@RequestBody AnalisisRequestDTO request) {
        try {
            AnalisisResponseDTO response = analisisService.analizarProducto(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /api/analisis/health - para verificar que el servicio está vivo
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("ms-analisis funcionando correctamente");
    }
}