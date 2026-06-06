package com.tarea.carwashapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Promocion(
    @SerialName("id_promocion") val id: Int? = null,
    val titulo: String,
    val subtitulo: String?,
    @SerialName("descripcion_descuento") val descripcionDescuento: String,
    @SerialName("imagen_banner_url") val imagenBannerUrl: String? = null,
    val activo: Boolean
)

@Serializable
data class Sucursal(
    @SerialName("id_sucursal") val id: Int? = null,
    val nombre: String,
    val direccion: String,
    val latitud: Double,
    val longitud: Double,
    @SerialName("imagen_url") val imagenUrl: String? = null,
    val descripcion: String?,
    @SerialName("calificacion_promedio") val calificacionPromedio: Double,
    @SerialName("total_resenas") val totalResenas: Int
)

@Serializable
data class Servicio(
    @SerialName("id_servicio") val id: Int? = null,
    val nombre: String,
    val categoria: String, // 'REGULAR', 'MEDIO', 'ESPECIAL'
    val descripcion: String?,
    val precio: Double,
    @SerialName("duracion_estimada_minutos") val duracionMinutos: Int
)