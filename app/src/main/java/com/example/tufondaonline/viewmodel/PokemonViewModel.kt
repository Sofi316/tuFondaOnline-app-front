package com.example.tufondaonline.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tufondaonline.model.PokemonResponse
import com.example.tufondaonline.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PokemonViewModel: ViewModel() {
    private val repository = PokemonRepository()
    private val _pokemonState = MutableStateFlow<PokemonResponse?>(null)
    val pokemonState: StateFlow<PokemonResponse?> = _pokemonState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        obtenerPokemonRandom()
    }

    fun obtenerPokemonRandom() {
        viewModelScope.launch {
            _isLoading.value = true
            val idRandom = (1..1025).random()
            val resultado = repository.recuperarPokemon(idRandom)
            _pokemonState.value = resultado
            _isLoading.value = false
        }
    }
}
