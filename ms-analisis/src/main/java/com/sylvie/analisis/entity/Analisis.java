package com.sylvie.analisis.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "analisis", schema = "analisis")
public class Analisis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_barras", nullable = false)
    private String codigoBarras;

    @Column(nullable = false)
    private Integer puntuacion;

    @Column(nullable = false)
    private String clasificacion; // BUENO, RIESGO_BAJO, REGULAR, RIESGO_ALTO, MALO

    @Column(columnDefinition = "TEXT")
    private String detalle;

    private LocalDateTime fecha = LocalDateTime.now();
}