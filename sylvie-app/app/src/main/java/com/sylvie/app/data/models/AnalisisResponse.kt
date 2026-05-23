package com.sylvie.app.data.models

data class AnalisisResponse(
    val codigoBarras: String,
    val puntuacion: Int,
    val clasificacion: String,
    val mensaje: String
)