package com.sylvie.usuarios.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String token;
    private String email;
    private String nombre;

    public LoginResponseDTO(String token, String email, String nombre) {
        this.token = token;
        this.email = email;
        this.nombre = nombre;
    }
}