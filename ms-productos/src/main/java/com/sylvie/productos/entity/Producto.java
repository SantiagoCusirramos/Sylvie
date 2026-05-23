package com.sylvie.productos.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "producto", schema = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String marca;
    private String categoria;

    @Column(name = "codigo_barras", nullable = false, unique = true)
    private String codigoBarras;

    @Column(columnDefinition = "TEXT")
    private String descripcion;
}