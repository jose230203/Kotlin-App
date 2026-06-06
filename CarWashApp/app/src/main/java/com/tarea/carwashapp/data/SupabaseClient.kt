package com.tarea.carwashapp.data

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.auth.Auth

object SupabaseClient {
    private const val SUPABASE_URL = "https://xukzwhcqlnsbnoafpxlq.supabase.co"
    private const val SUPABASE_ANON_KEY = "sb_publishable_e9I-V2lOSu_z9EtI0ffVOw__iOdpIKL"

    val client = createSupabaseClient(SUPABASE_URL, SUPABASE_ANON_KEY) {
        install(Postgrest)
        install(Auth)
    }
}