package com.tarea.carwashapp.ui

import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tarea.carwashapp.R
import com.tarea.carwashapp.data.Sucursal
import com.tarea.carwashapp.data.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MapaActivity : AppCompatActivity() {

    private lateinit var mapa: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // REQUISITO IMPORTANTE PARA OSMDROID: Cargar la configuración del agente de usuario
        // Esto evita que OpenStreetMap bloquee las peticiones de tu app.
        val ctx = applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))

        setContentView(R.layout.activity_mapa)

        // 1. Inicializar el MapView desde tu XML
        mapa = findViewById(R.id.mapaOsm)
        mapa.setMultiTouchControls(true) // Permitir pellizcar para hacer zoom

        // 2. Centrar el mapa por defecto en Managua, Nicaragua
        val mapaController = mapa.controller
        mapaController.setZoom(13.0) // Un nivel de zoom ideal para ver calles de la ciudad
        val puntoManagua = GeoPoint(12.1150, -86.2362) // Coordenadas aproximadas del centro de Managua
        mapaController.setCenter(puntoManagua)

        // 3. Jalar los locales de Supabase y pintar los pines
        cargarPinesDesdeSupabase()
    }

    private fun cargarPinesDesdeSupabase() {
        lifecycleScope.launch {
            try {
                // Traemos la lista de sucursales desde tu BD de la misma forma que en el Home
                val listaSucursales = withContext(Dispatchers.IO) {
                    SupabaseClient.client.from("sucursales")
                        .select()
                        .decodeList<Sucursal>()
                }

                // Recorremos la lista para colocar un marcador por cada Carwash
                for (sucursal in listaSucursales) {
                    val pin = Marker(mapa)
                    pin.position = GeoPoint(sucursal.latitud, sucursal.longitud)
                    pin.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                    // Configurar los textos que salen al tocar el pin (Bocadillo flotante)
                    pin.title = sucursal.nombre
                    pin.subDescription = sucursal.direccion

                    // Acción opcional: Si quieres hacer algo al tocar la ventana informativa del pin
                    pin.setOnMarkerClickListener { marker, mapView ->
                        marker.showInfoWindow()
                        true
                    }

                    // Añadir el pin al mapa
                    mapa.overlays.add(pin)
                }

                // Refrescar el mapa para que pinte los marcadores de inmediato
                mapa.invalidate()

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    this@MapaActivity,
                    "Error al cargar pines de Supabase: ${e.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    // Gestionar correctamente el ciclo de vida del mapa para que no consuma RAM de más
    override fun onResume() {
        super.onResume()
        mapa.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapa.onPause()
    }
}