package com.sylvie.usuarios.dto;

import lombok.Data;

@Data
public class RestriccionDTO {
    private String ingrediente;
    private String tipo; // alergia, intolerancia o preferncias
}