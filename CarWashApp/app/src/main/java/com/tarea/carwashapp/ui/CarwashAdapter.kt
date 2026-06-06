package com.tarea.carwashapp.ui
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tarea.carwashapp.R
import com.tarea.carwashapp.data.Sucursal

class CarwashAdapter(
    private val listaSucursales: List<Sucursal>,
    private val onReservaClick: (Sucursal) -> Unit
) : RecyclerView.Adapter<CarwashAdapter.CarwashViewHolder>() {

    class CarwashViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgCarwash: ImageView = view.findViewById(R.id.imgCarwash)
        val txtNombre: TextView = view.findViewById(R.id.txtNombreCarwash)
        val txtDireccion: TextView = view.findViewById(R.id.txtDireccion)
        val txtCalificacion: TextView = view.findViewById(R.id.txtCalificacion)
        val btnReserva: Button = view.findViewById(R.id.btnReservaItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarwashViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_carwash, parent, false)
        return CarwashViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarwashViewHolder, position: Int) {
        val sucursal = listaSucursales[position]

        holder.txtNombre.text = sucursal.nombre
        holder.txtDireccion.text = sucursal.direccion

        val votosK = if (sucursal.totalResenas >= 1000) "${String.format("%.1f", sucursal.totalResenas / 1000.0)}k" else sucursal.totalResenas.toString()
        holder.txtCalificacion.text = "${sucursal.calificacionPromedio} ($votosK)"


        holder.btnReserva.setOnClickListener {
            onReservaClick(sucursal)
        }
    }

    override fun getItemCount(): Int = listaSucursales.size
}