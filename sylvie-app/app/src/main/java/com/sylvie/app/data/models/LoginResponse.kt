package com.sylvie.app.data.models

data class LoginResponse(
    val token: String,
    val email: String,
    val nombre: String
)