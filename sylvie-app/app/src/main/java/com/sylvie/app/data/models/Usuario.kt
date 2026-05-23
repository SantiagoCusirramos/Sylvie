package com.sylvie.app.data.models

data class Usuario(
    val id: Long,
    val nombre: String,
    val email: String
)

data class RegistroRequest(
    val nombre: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class RestriccionRequest(
    val ingrediente: String,
    val tipo: String
)