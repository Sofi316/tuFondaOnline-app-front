package com.example.tufondaonline.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tufondaonline.model.Producto
import com.example.tufondaonline.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductoViewModel : ViewModel() {

    private val repository = ProductoRepository()

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    private val _categorias = MutableStateFlow<List<String>>(emptyList())
    val categorias: StateFlow<List<String>> = _categorias.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        cargarProductos()
    }

    fun cargarProductos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.obtenerProductos()
                if (response.isSuccessful && response.body() != null) {
                    val listaProductos = response.body()!!
                    _productos.value = listaProductos


                    val listaCategorias = listaProductos
                        .mapNotNull { it.categoria?.nombre }
                        .distinct()
                        .sorted()

                    _categorias.value = listaCategorias
                }
            } catch (e: Exception) {
            } finally {
                _isLoading.value = false
            }
        }
    }
}