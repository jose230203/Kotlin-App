package com.tarea.asistenciaapp.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsistenciaRepository {
    suspend fun marcarAsistencia(sesionId: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val body = mapOf("sesion_id" to sesionId)
            val response = RetrofitClient.instance.registrarAsistencia(body)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }
}