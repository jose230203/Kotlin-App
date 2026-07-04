package com.tarea.asistenciaapp.data

import android.content.Context
import android.content.SharedPreferences

object AuthManager {
    private const val PREF_NAME = "auth_prefs"
    private const val TOKEN_KEY = "auth_token"
    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveToken(token: String) = prefs.edit().putString(TOKEN_KEY, token).apply()
    fun getToken(): String? = prefs.getString(TOKEN_KEY, null)
}