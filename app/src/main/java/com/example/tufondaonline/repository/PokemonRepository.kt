package com.example.tufondaonline.repository

import com.example.tufondaonline.model.PokemonResponse
import com.example.tufondaonline.remote.RetrofitInstance

class PokemonRepository {
    private val apiService= RetrofitInstance.apiPubli

    suspend fun recuperarPokemon(id: Int): PokemonResponse?{
        return try {
            val response = apiService.obtenerPokemon(id)
            if(response.isSuccessful){
                return response.body()
            }else{
                null
            }
        }catch (e: Exception){
            null
        }
    }
}
