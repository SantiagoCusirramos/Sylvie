package com.sylvie.app.data.api

import com.sylvie.app.data.models.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    // usuarios
    @POST("/api/usuarios/registro")
    fun registrar(@Body request: RegistroRequest): Call<Usuario>

    @GET("/api/usuarios/perfil/{id}")
    fun obtenerPerfil(@Path("id") id: Long): Call<Usuario>

    @GET("/api/usuarios/restricciones/{usuarioId}")
    fun obtenerRestricciones(@Path("usuarioId") usuarioId: Long): Call<List<RestriccionRequest>>

    @POST("/api/usuarios/restricciones/{usuarioId}")
    fun agregarRestriccion(
        @Path("usuarioId") usuarioId: Long,
        @Body request: RestriccionRequest
    ): Call<RestriccionRequest>

    // productos
    @GET("/api/productos/barras/{codigo}")
    fun buscarProducto(@Path("codigo") codigo: String): Call<Producto>

    // analisis
    @POST("/api/analisis/analizar")
    fun analizarProducto(@Body request: Map<String, String>): Call<AnalisisResponse>

    // recomendaciones
    @POST("/api/recomendaciones/generar")
    fun generarRecomendacion(@Body request: Map<String, Any>): Call<RecomendacionResponse>
}