package com.sylvie.recomendaciones.dto;

import lombok.Data;

@Data
public class AnalisisResultadoDTO {
    private String codigoBarras;
    private Integer puntuacion;
    private String clasificacion;
    private String mensaje;
}