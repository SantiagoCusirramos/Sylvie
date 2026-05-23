package com.sylvie.recomendaciones.dto;

import lombok.Data;

@Data
public class RestriccionDTO {
    private String ingrediente;
    private String tipo; // alergia - introlerancia - repferencias
}