package com.example.tufondaonline.model

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    val id: Int,
    val name: String,
    val sprites: PokemonSprites
)

data class PokemonSprites(
    @SerializedName("front_default")
    val fotoUrl: String
)
