package com.sylvie.recomendaciones.dto;

import lombok.Data;
import java.util.List;

@Data
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String email;
    private List<RestriccionDTO> restricciones;
}