package com.sylvie.usuarios.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "restriccion", schema = "usuarios")
public class Restriccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(nullable = false)
    private String ingrediente;

    @Column(nullable = false)
    private String tipo; // este enfocalo como alergia, intolerancia o preferncias
}