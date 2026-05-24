package com.sylvie.app.data.models

data class RestriccionResponse(
    val id: Long,
    val ingrediente: String,
    val tipo: String,
    val usuarioId: Long
)