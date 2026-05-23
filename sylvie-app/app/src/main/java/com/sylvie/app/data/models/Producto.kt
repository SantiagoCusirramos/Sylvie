package com.sylvie.app.data.models

data class Producto(
    val id: Long,
    val nombre: String,
    val marca: String,
    val categoria: String,
    val codigoBarras: String,
    val descripcion: String
)