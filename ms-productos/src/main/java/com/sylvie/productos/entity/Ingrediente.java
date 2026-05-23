package com.sylvie.productos.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ingrediente", schema = "productos")
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    private String tipo;
    private String descripcion;

    @Column(name = "nivel_riesgo", nullable = false)
    private String nivelRiesgo;
}