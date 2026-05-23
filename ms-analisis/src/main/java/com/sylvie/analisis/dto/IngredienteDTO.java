package com.sylvie.analisis.dto;

import lombok.Data;

@Data
public class IngredienteDTO {
    private Long id;
    private String nombre;
    private String tipo;
    private String nivelRiesgo; // bajo - medio - alto
}