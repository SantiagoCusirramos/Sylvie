package com.sylvie.app.data.repository

import com.sylvie.app.data.api.RetrofitInstance
import com.sylvie.app.data.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class SylvieRepository {

    // ========== USUARIOS ==========
    suspend fun registrarUsuario(nombre: String, email: String, password: String): Result<Usuario> {
        return withContext(Dispatchers.IO) {
            try {
                val request = RegistroRequest(nombre, email, password)
                val response = RetrofitInstance.api.registrar(request)
                if (response.isSuccessful) {
                    response.body()?.let {
                        Result.success(it)
                    } ?: Result.failure(Exception("Respuesta vacía"))
                } else {
                    Result.failure(Exception("Error ${response.code()}: ${response.message()}"))
                }
            } catch (e: IOException) {
                Result.failure(Exception("Error de red: ${e.message}"))
            } catch (e: HttpException) {
                Result.failure(Exception("Error del servidor: ${e.code()}"))
            }
        }
    }

    suspend fun agregarRestriccion(usuarioId: Long, ingrediente: String, tipo: String): Result<RestriccionRequest> {
        return withContext(Dispatchers.IO) {
            try {
                val request = RestriccionRequest(ingrediente, tipo)
                val response = RetrofitInstance.api.agregarRestriccion(usuarioId, request)
                if (response.isSuccessful) {
                    response.body()?.let {
                        Result.success(it)
                    } ?: Result.failure(Exception("Respuesta vacía"))
                } else {
                    Result.failure(Exception("Error ${response.code()}: ${response.message()}"))
                }
            } catch (e: IOException) {
                Result.failure(Exception("Error de red: ${e.message}"))
            } catch (e: HttpException) {
                Result.failure(Exception("Error del servidor: ${e.code()}"))
            }
        }
    }

    suspend fun obtenerRestricciones(usuarioId: Long): Result<List<RestriccionRequest>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.obtenerRestricciones(usuarioId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        Result.success(it)
                    } ?: Result.success(emptyList())
                } else {
                    Result.failure(Exception("Error ${response.code()}: ${response.message()}"))
                }
            } catch (e: IOException) {
                Result.failure(Exception("Error de red: ${e.message}"))
            } catch (e: HttpException) {
                Result.failure(Exception("Error del servidor: ${e.code()}"))
            }
        }
    }

    // ========== PRODUCTOS ==========
    suspend fun buscarProducto(codigoBarras: String): Result<Producto> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.buscarProducto(codigoBarras)
                if (response.isSuccessful) {
                    response.body()?.let {
                        Result.success(it)
                    } ?: Result.failure(Exception("Producto no encontrado"))
                } else {
                    Result.failure(Exception("Error ${response.code()}: ${response.message()}"))
                }
            } catch (e: IOException) {
                Result.failure(Exception("Error de red: ${e.message}"))
            } catch (e: HttpException) {
                Result.failure(Exception("Error del servidor: ${e.code()}"))
            }
        }
    }

    // ========== ANÁLISIS ==========
    suspend fun analizarProducto(codigoBarras: String): Result<AnalisisResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.analizarProducto(
                    mapOf("codigoBarras" to codigoBarras)
                )
                if (response.isSuccessful) {
                    response.body()?.let {
                        Result.success(it)
                    } ?: Result.failure(Exception("Análisis fallido"))
                } else {
                    Result.failure(Exception("Error ${response.code()}: ${response.message()}"))
                }
            } catch (e: IOException) {
                Result.failure(Exception("Error de red: ${e.message}"))
            } catch (e: HttpException) {
                Result.failure(Exception("Error del servidor: ${e.code()}"))
            }
        }
    }

    // ========== RECOMENDACIONES ==========
    suspend fun generarRecomendacion(usuarioId: Long, codigoBarras: String): Result<RecomendacionResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.generarRecomendacion(
                    mapOf("usuarioId" to usuarioId, "codigoBarras" to codigoBarras)
                )
                if (response.isSuccessful) {
                    response.body()?.let {
                        Result.success(it)
                    } ?: Result.failure(Exception("Recomendación fallida"))
                } else {
                    Result.failure(Exception("Error ${response.code()}: ${response.message()}"))
                }
            } catch (e: IOException) {
                Result.failure(Exception("Error de red: ${e.message}"))
            } catch (e: HttpException) {
                Result.failure(Exception("Error del servidor: ${e.code()}"))
            }
        }
    }
}