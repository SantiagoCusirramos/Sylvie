package com.sylvie.recomendaciones.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "recomendacion", schema = "recomendaciones")
public class Recomendacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "codigo_barras", nullable = false)
    private String codigoBarras;

    @Column(nullable = false)
    private String resultado; // APTO, NO_APTO, USAR_CON_PRECAUCION

    @Column(columnDefinition = "TEXT")
    private String motivo;

    private LocalDateTime fecha = LocalDateTime.now();
}