package com.sylvie.analisis.dto;

import lombok.Data;

@Data
public class AnalisisResponseDTO {
    private String codigoBarras;
    private Integer puntuacion;
    private String clasificacion;
    private String mensaje;

    public AnalisisResponseDTO(String codigoBarras, Integer puntuacion, String clasificacion, String mensaje) {
        this.codigoBarras = codigoBarras;
        this.puntuacion = puntuacion;
        this.clasificacion = clasificacion;
        this.mensaje = mensaje;
    }
}