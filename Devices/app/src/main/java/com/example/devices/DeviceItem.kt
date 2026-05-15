package com.example.devices

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.devices.ui.theme.DevicesTheme
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.devices.ui.theme.Typography

@Composable
fun DeviceItemView(device: Device){
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(end = 16.dp, top = 16.dp ,bottom =16.dp)) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_phone_24),
            contentDescription = "Icono de teléfono",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    Column() {

        Text(text = device.name,
            style = Typography.headlineLarge
        )
        if(device.data?.color !=null)
        {
            Text(text = device.data.color,
                style = Typography.bodyMedium
            )
        }
        if(device.data?.capacity !=null)
        {
            Text(text = device.data.capacity,
                style = Typography.bodyMedium
            )
        }
        if(device.data?.price !=null)
        {
            Text(text = "$"+device.data.price.toString(),
                style = Typography.bodyMedium
            )
        }



        HorizontalDivider()
        }

    }

}

@Preview(showBackground = true)
@Composable
fun DeviceItemPreview(){
    DevicesTheme {
        DeviceItemView(device = Device(id=1,name="Nexus", data = Specs(color="black",capacity="64 gb", price =300.4)))
    }
}