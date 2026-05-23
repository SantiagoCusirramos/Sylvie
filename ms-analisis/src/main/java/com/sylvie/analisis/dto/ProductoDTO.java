package com.sylvie.analisis.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String marca;
    private String categoria;
    private String codigoBarras;
    private String descripcion;
    private List<IngredienteDTO> ingredientes;
}