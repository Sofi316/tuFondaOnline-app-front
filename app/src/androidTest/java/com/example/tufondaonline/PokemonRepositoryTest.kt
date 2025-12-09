package com.example.tufondaonline

import com.example.tufondaonline.model.PokemonResponse
import com.example.tufondaonline.model.PokemonSprites
import com.example.tufondaonline.remote.PokeApiService
import com.example.tufondaonline.repository.PokemonRepositoryT
import io.mockk.mockk
import io.mockk.coEvery
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response

class PokemonRepositoryTest {
    private val mockApiService = mockk<PokeApiService>()
    private val repository = PokemonRepositoryT(mockApiService)

    @Test
    fun recuperar_pokemon_por_id()= runTest{
        val id=405

        val mockResponse = Response.success(PokemonResponse(id,"luxray",
            PokemonSprites(fotoUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/405.png")
        ))
        coEvery { mockApiService.obtenerPokemon(id) } returns mockResponse

        val resultado = repository.recuperarPokemon(id)

        assertEquals(mockResponse.body(), resultado)







    }
}