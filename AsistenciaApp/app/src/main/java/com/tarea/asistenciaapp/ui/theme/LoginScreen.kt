package com.tarea.asistenciaapp.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tarea.asistenciaapp.data.AuthManager
import com.tarea.asistenciaapp.data.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    var error by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp).fillMaxSize(), verticalArrangement = Arrangement.Center) {
        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = password, onValueChange = { password = it }, label = { Text("Contraseña") }, modifier = Modifier.fillMaxWidth())

        if (error.isNotEmpty()) Text(error, color = Color.Red)

        Button(
            onClick = {
                scope.launch {
                    try {
                        val body = mapOf("email" to email, "password" to password)
                        val response = RetrofitClient.instance.login(body)
                        AuthManager.saveToken(response.token)
                        onLoginSuccess()
                    } catch (e: Exception) {
                        error = "Error al iniciar sesión: ${e.message}"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) { Text("Entrar") }
    }
}