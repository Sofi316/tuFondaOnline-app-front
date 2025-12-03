package com.example.tufondaonline.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val apiBack: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.100.53:8080/")
        //Debes usar la IP Local de tu PC, Ambos dispositivos deben estar en la misma red Wi-Fi y tu firewall de Windows debe permitir conexiones entrantes al puerto 8080.
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    val apiPubli: PokeApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokeApiService::class.java)
    }
}