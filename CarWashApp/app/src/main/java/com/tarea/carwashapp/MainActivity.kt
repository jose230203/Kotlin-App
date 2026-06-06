package com.tarea.carwashapp

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tarea.carwashapp.data.Promocion
import com.tarea.carwashapp.data.Sucursal
import com.tarea.carwashapp.data.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.postgrest.result.PostgrestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.tarea.carwashapp.ui.CarwashAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var rvCarwashes: RecyclerView
    private lateinit var adapter: CarwashAdapter
    private lateinit var txtBienvenida: TextView
    private lateinit var txtOfertaDesc: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Inicializar los componentes visuales del XML
        rvCarwashes = findViewById(R.id.rvCarwashes)
        txtBienvenida = findViewById(R.id.txtBienvenida)
        txtOfertaDesc = findViewById(R.id.txtOfertaDesc)

        // 2. Configurar el RecyclerView (Lista vertical)
        rvCarwashes.layoutManager = LinearLayoutManager(this)

        // 3. Cargar los datos desde Supabase
        cargarDatosDesdeSupabase()
    }

    private fun cargarDatosDesdeSupabase() {
        // Usamos lifecycleScope.launch para ejecutar la petición de internet en una corrutina
        // y evitar que la aplicación se congele o se cierre (Anr).
        lifecycleScope.launch {
            try {
                // --- CONSULTA 1: Traer el banner de promoción activo ---
                val promocionActiva = withContext(Dispatchers.IO) {
                    SupabaseClient.client.from("promociones")
                        .select {
                            filter {
                                eq("activo", true)
                            }
                        }.decodeSingleOrNull<Promocion>()
                }

                val listaSucursales = withContext(Dispatchers.IO) {
                    SupabaseClient.client.from("sucursales")
                        .select()
                        .decodeList<Sucursal>()
                }

                promocionActiva?.let {
                    txtBienvenida.text = "¡¡¡ ${it.titulo} !!!"
                    txtOfertaDesc.text = it.descripcionDescuento
                }

                adapter = CarwashAdapter(listaSucursales) { sucursalSeleccionada ->
                    Toast.makeText(
                        this@MainActivity,
                        "Reservando en: ${sucursalSeleccionada.nombre}",
                        Toast.LENGTH_SHORT
                    ).show()

                }

                rvCarwashes.adapter = adapter

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    this@MainActivity,
                    "Error al conectar con Supabase: ${e.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}