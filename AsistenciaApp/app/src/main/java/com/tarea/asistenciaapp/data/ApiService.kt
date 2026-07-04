package com.tarea.asistenciaapp.data

import retrofit2.Response
import retrofit2.http.*

data class LoginResponse(val token: String)

interface ApiService {
    @POST("/auth/login")
    suspend fun login(@Body body: Map<String, String>): LoginResponse

    @Headers("Content-Type: application/json")
    @POST("/inscripciones/unirse")
    suspend fun unirseASala(
        @Header("Authorization") token: String,
        @Body body: Map<String, String>
    ): Response<Void>

    @POST("/asistencias/registrar")
    suspend fun registrarAsistencia(@Body body: Map<String, String>): Response<Void>

    @GET("/calificaciones/mis-notas")
    suspend fun obtenerMisNotas(): List<Map<String, Any>>
}