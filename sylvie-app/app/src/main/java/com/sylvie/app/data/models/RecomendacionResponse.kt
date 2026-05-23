package com.sylvie.app.data.models

data class RecomendacionResponse(
    val resultado: String,
    val motivo: String,
    val puntuacion: Int,
    val clasificacion: String
)