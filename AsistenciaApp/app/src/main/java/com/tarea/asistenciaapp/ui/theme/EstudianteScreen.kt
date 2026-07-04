package com.tarea.asistenciaapp.ui.theme

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tarea.asistenciaapp.data.AuthManager
import com.tarea.asistenciaapp.data.RetrofitClient
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // IMPORTANTE: Este es necesario

val AzulWeb = Color(0xFF2563EB)
val RojoWeb = Color(0xFFDC2626)
@Composable
fun EstudianteScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Asistencia", "Notas", "Salas")

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Mi Dashboard", style = MaterialTheme.typography.headlineMedium, color = AzulWeb)

        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Contenedor principal que cambia según la tab
        Box(modifier = Modifier.fillMaxSize()) {
            when (selectedTab) {
                0 -> PantallaAsistencia()
                1 -> PantallaNotas()
                2 -> PantallaUnirseSalas()
            }
        }
    }
}

@Composable
fun PantallaAsistencia() {
    Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
        Text("Estado: Presente", style = MaterialTheme.typography.headlineSmall, color = Color(0xFF166534))
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* Acción */ }, colors = ButtonDefaults.buttonColors(containerColor = RojoWeb)) {
            Text("FINALIZAR SESIÓN")
        }
    }
}
@Composable
fun PantallaNotas() {
    val notas = listOf("Programación: 9.5", "Base de Datos: 8.0", "Inglés: 9.0")

    // Usamos LazyColumn con items(lista) en lugar de items(size)
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(notas) { nota ->
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                Text(
                    text = nota,
                    modifier = Modifier.padding(20.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
@Composable
fun PantallaUnirseSalas() {
    var codigo by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var feedback by remember { mutableStateOf("") } // Estado para mensaje

    Column {
        OutlinedTextField(
            value = codigo,
            onValueChange = { codigo = it.uppercase() },
            label = { Text("Código de sala") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (codigo.isBlank()) {
                    feedback = "Ingresa un código"
                } else {
                    isLoading = true
                    // Simulamos lógica de red
                    kotlinx.coroutines.GlobalScope.launch {
                        kotlinx.coroutines.delay(1000)
                        feedback = "¡Te has unido a $codigo!"
                        isLoading = false
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            if (isLoading) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            else Text("UNIRSE")
        }

        if (feedback.isNotEmpty()) {
            Text(feedback, modifier = Modifier.padding(top = 8.dp), color = AzulWeb)
        }
    }
}