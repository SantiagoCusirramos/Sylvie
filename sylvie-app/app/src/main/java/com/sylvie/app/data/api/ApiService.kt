package com.sylvie.app.data.api

import com.sylvie.app.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // ========== USUARIOS ==========
    @POST("/api/usuarios/registro")
    suspend fun registrar(@Body request: RegistroRequest): Response<Usuario>

    @POST("/api/usuarios/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("/api/usuarios/perfil/{id}")
    suspend fun obtenerPerfil(@Path("id") id: Long): Response<Usuario>

    @GET("/api/usuarios/restricciones/{usuarioId}")
    suspend fun obtenerRestricciones(@Path("usuarioId") usuarioId: Long): Response<List<RestriccionResponse>>

    @POST("/api/usuarios/restricciones/{usuarioId}")
    suspend fun agregarRestriccion(
        @Path("usuarioId") usuarioId: Long,
        @Body request: RestriccionRequest
    ): Response<RestriccionResponse>

    @DELETE("/api/usuarios/restricciones/{usuarioId}/{ingrediente}")
    suspend fun eliminarRestriccion(
        @Path("usuarioId") usuarioId: Long,
        @Path("ingrediente") ingrediente: String
    ): Response<Unit>

    // ========== PRODUCTOS ==========
    @GET("/api/productos/barras/{codigo}")
    suspend fun buscarProducto(@Path("codigo") codigo: String): Response<Producto>

    @GET("/api/productos")
    suspend fun listarProductos(): Response<List<Producto>>

    // ========== ANÁLISIS ==========
    @POST("/api/analisis/analizar")
    suspend fun analizarProducto(@Body request: AnalisisRequest): Response<AnalisisResponse>

    // ========== RECOMENDACIONES ==========
    @POST("/api/recomendaciones/generar")
    suspend fun generarRecomendacion(@Body request: RecomendacionRequest): Response<RecomendacionResponse>

    @GET("/api/recomendaciones/historial/{usuarioId}")
    suspend fun obtenerHistorial(@Path("usuarioId") usuarioId: Long): Response<List<RecomendacionHistorial>>
}