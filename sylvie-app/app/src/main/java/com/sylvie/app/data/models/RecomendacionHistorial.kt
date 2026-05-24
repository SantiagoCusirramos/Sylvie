package com.sylvie.app.data.models

data class RecomendacionHistorial(
    val id: Long,
    val usuarioId: Long,
    val codigoBarras: String,
    val resultado: String,
    val motivo: String,
    val fecha: String
)