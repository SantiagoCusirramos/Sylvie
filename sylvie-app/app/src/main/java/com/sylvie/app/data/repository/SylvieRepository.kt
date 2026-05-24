package com.sylvie.app.data.repository

import com.sylvie.app.data.api.RetrofitInstance
import com.sylvie.app.data.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class SylvieRepository {

    // ========== USUARIOS ==========
    suspend fun registrarUsuario(nombre: String, email: String, password: String): Result<Usuario> {
        return withContext(Dispatchers.IO) {
            try {
                val request = RegistroRequest(nombre, email, password)
                val response = RetrofitInstance.api.registrar(request)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Error ${response.code()}: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(Exception("Error de conexión: ${e.message}"))
            }
        }
    }

    suspend fun loginUsuario(email: String, password: String): Result<LoginResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val request = LoginRequest(email, password)
                val response = RetrofitInstance.api.login(request)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Credenciales incorrectas"))
                }
            } catch (e: Exception) {
                Result.failure(Exception("Error de conexion: ${e.message}"))
            }
        }
    }



    suspend fun agregarRestriccion(usuarioId: Long, ingrediente: String, tipo: String): Result<RestriccionResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val request = RestriccionRequest(ingrediente, tipo)
                val response = RetrofitInstance.api.agregarRestriccion(usuarioId, request)
                if(response.isSuccessful && response.body() != null){
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Error al agregar la restriccion"))
                }
            } catch (e: Exception){
                Result.failure(Exception("Error de conexcion: ${e.message}"))
            }
        }
    }

    suspend fun obtenerRestricciones(usuarioId: Long): Result<List<RestriccionResponse>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.obtenerRestricciones(usuarioId)
                if (response.isSuccessful) {
                    Result.success(response.body() ?: emptyList())
                } else {
                    Result.failure(Exception("Error al obtener restricciones"))
                }
            } catch (e: IOException) {
                Result.failure(Exception("Error de conexion: ${e.message}"))
            }
        }
    }

    // ========== PRODUCTOS ==========
    suspend fun buscarProducto(codigoBarras: String): Result<Producto> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.buscarProducto(codigoBarras)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Error: Producto no encontrado"))
                }
            } catch (e: IOException) {
                Result.failure(Exception("Error de red: ${e.message}"))
            }
        }
    }

    // ========== ANÁLISIS ==========
    suspend fun analizarProducto(codigoBarras: String): Result<AnalisisResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val request = AnalisisRequest(codigoBarras)
                val response = RetrofitInstance.api.analizarProducto(request)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Error al analizar producto"))
                }
            } catch (e: Exception) {
                Result.failure(Exception("Error de conexión: ${e.message}"))
            }
        }
    }

    // ========== RECOMENDACIONES ==========
    suspend fun generarRecomendacion(usuarioId: Long, codigoBarras: String): Result<RecomendacionResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val request = RecomendacionRequest(usuarioId, codigoBarras)
                val response = RetrofitInstance.api.generarRecomendacion(request)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Error al generar recomendación"))
                }
            } catch (e: Exception) {
                Result.failure(Exception("Error de conexión: ${e.message}"))
            }
        }
    }

    suspend fun obtenerHistorial(usuarioId: Long): Result<List<RecomendacionHistorial>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.obtenerHistorial(usuarioId)
                if (response.isSuccessful) {
                    Result.success(response.body() ?: emptyList())
                } else {
                    Result.failure(Exception("Error al obtener historial"))
                }
            } catch (e: Exception) {
                Result.failure(Exception("Error de conexión: ${e.message}"))
            }
        }
    }
}