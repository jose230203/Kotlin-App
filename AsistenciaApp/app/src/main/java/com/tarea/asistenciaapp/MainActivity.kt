package com.tarea.asistenciaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tarea.asistenciaapp.data.AuthManager
import com.tarea.asistenciaapp.ui.theme.AsistenciaAppTheme
import com.tarea.asistenciaapp.ui.theme.EstudianteScreen
import com.tarea.asistenciaapp.ui.theme.LoginScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AuthManager.init(applicationContext) //

        setContent {
            var isLoggedIn by remember { mutableStateOf(AuthManager.getToken() != null) }

            if (isLoggedIn) {
                EstudianteScreen()
            } else {
                LoginScreen(onLoginSuccess = { isLoggedIn = true })
            }
        }
    }
}