package com.sylvie.recomendaciones.dto;

import lombok.Data;

@Data
public class RecomendacionRequestDTO {
    private Long usuarioId;
    private String codigoBarras;
}