package com.example.tufondaonline.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tufondaonline.model.Categoria
import com.example.tufondaonline.model.Producto
import com.example.tufondaonline.repository.AdminProductosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminProductosViewModel : ViewModel() {

    private val repository = AdminProductosRepository()

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    var productoEditando by mutableStateOf<Producto?>(null)
    var mostrarDialogo by mutableStateOf(false)

    private val _categorias = MutableStateFlow<List<Categoria>>(emptyList())
    val categorias: StateFlow<List<Categoria>> = _categorias.asStateFlow()

    init {
        cargarProductos()
        cargarCategorias()
    }

    fun cargarProductos() {
        viewModelScope.launch {
            _isLoading.value = true
            val response = repository.obtenerProductos()
            if (response.isSuccessful) {
                _productos.value = response.body() ?: emptyList()
            }
            _isLoading.value = false
        }
    }

    fun crearProducto(producto: Producto) {
        viewModelScope.launch {
            try {
                val response = repository.crearProducto(producto)
                if (response.isSuccessful) {
                    cargarProductos()
                } else {
                    Log.e("AdminProductosVM", "Error al crear producto: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("AdminProductosVM", "Excepción al crear producto: ${e.message}")
            }
        }
    }
    fun cargarCategorias() {
        viewModelScope.launch {
            try {
                val response = repository.obtenerCategorias()
                if (response.isSuccessful) {
                    _categorias.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                Log.e("ProductoVM", "Error cargando categorías: ${e.message}")
            }
        }
    }

    fun editarProducto(producto: Producto) {
        viewModelScope.launch {
            val response = repository.actualizarProducto(producto.id, producto)
            if (response.isSuccessful) {
                cargarProductos()
            }
        }
    }

    fun eliminarProducto(id: Long) {
        viewModelScope.launch {
            val response = repository.eliminarProducto(id)
            if (response.isSuccessful) {
                cargarProductos()
            }
        }
    }
}
