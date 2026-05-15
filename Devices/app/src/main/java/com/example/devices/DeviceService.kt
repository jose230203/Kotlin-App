package com.example.devices

import retrofit2.http.GET

interface DeviceService {
    @GET(Constants.OBJECT_PATH)
    suspend fun getAllDevice() :List<Device>
}