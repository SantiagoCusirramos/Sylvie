package com.sylvie.recomendaciones.dto;

import lombok.Data;

@Data
public class RecomendacionResponseDTO {
    private String resultado;
    private String motivo;
    private Integer puntuacion;
    private String clasificacion;

    public RecomendacionResponseDTO(String resultado, String motivo,
                                    Integer puntuacion, String clasificacion) {
        this.resultado = resultado;
        this.motivo = motivo;
        this.puntuacion = puntuacion;
        this.clasificacion = clasificacion;
    }
}