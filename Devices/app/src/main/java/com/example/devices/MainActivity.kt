package com.example.devices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.devices.ui.theme.DevicesTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DevicesTheme {
                var devices by remember { mutableStateOf(listOf<Device>()) }
                var isLoading by remember {mutableStateOf(true)}
                getDevices { result ->
                    if(result !=null){
                    devices = result }
                    isLoading = false
                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        MainView(Modifier.padding(paddingValues = innerPadding),devices = devices)
                       if(isLoading)
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
    private fun getDevices(onResult: (List<Device>?)-> Unit){
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL).addConverterFactory( GsonConverterFactory.create()).build()

        val service = retrofit.create(DeviceService::class.java)
        var devices : List<Device>? = null
        lifecycleScope.launch {
            val devices= service.getAllDevice()

            onResult(devices)
        }
    }
}

