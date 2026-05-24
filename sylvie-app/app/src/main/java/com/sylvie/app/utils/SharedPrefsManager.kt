package com.sylvie.app.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefsManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("sylvie_prefs", Context.MODE_PRIVATE)

    fun guardarToken(token: String) {
        prefs.edit().putString("token", token).apply()
    }

    fun obtenerToken(): String? {
        return prefs.getString("token", null)
    }

    fun guardarEmail(email: String) {
        prefs.edit().putString("email", email).apply()
    }

    fun obtenerEmail(): String? {
        return prefs.getString("email", null)
    }

    fun guardarNombre(nombre: String) {
        prefs.edit().putString("nombre", nombre).apply()
    }

    fun obtenerNombre(): String? {
        return prefs.getString("nombre", null)
    }

    fun guardarUsuarioId(id: Long) {
        prefs.edit().putLong("usuario_id", id).apply()
    }

    fun obtenerUsuarioId(): Long {
        return prefs.getLong("usuario_id", -1)
    }

    fun limpiarSesion() {
        prefs.edit().clear().apply()
    }

    fun estaLogueado(): Boolean {
        return obtenerToken() != null
    }
}