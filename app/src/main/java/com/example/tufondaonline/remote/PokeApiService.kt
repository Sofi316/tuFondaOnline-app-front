package com.example.tufondaonline.remote

import com.example.tufondaonline.model.PokemonResponse
import retrofit2.Response
import retrofit2.http.*

interface PokeApiService {
    @GET("pokemon/{id}")
    suspend fun obtenerPokemon(@Path("id") id: Int): Response<PokemonResponse>
}