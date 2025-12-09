package com.example.tufondaonline.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tufondaonline.model.CarritoItem
import com.example.tufondaonline.model.OrdenRequest
import com.example.tufondaonline.model.Producto
import com.example.tufondaonline.model.UsuarioId
import com.example.tufondaonline.model.OrdenId
import com.example.tufondaonline.model.ProductoId
import com.example.tufondaonline.model.DetalleEnvio
import com.example.tufondaonline.repository.CarritoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CarritoViewModel : ViewModel() {

    private val repository = CarritoRepository()

    private val _items = MutableStateFlow<List<CarritoItem>>(emptyList())
    val items: StateFlow<List<CarritoItem>> = _items.asStateFlow()

    private val _totalCompra = MutableStateFlow(0)
    val totalCompra: StateFlow<Int> = _totalCompra.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _compraExitosa = MutableStateFlow(false)
    val compraExitosa: StateFlow<Boolean> = _compraExitosa.asStateFlow()



    fun agregarProducto(producto: Producto) {
        val listaActual = _items.value.toMutableList()

        val index = listaActual.indexOfFirst { it.producto.id == producto.id }

        if (index != -1) {
            val itemActual = listaActual[index]
            listaActual[index] = itemActual.copy(cantidad = itemActual.cantidad + 1)
        } else {
            listaActual.add(CarritoItem(producto, 1))
        }

        _items.value = listaActual
        calcularTotal()
    }

    fun quitarProducto(producto: Producto) {
        val listaActual = _items.value.toMutableList()
        val index = listaActual.indexOfFirst { it.producto.id == producto.id }

        if (index != -1) {
            val itemActual = listaActual[index]

            if (itemActual.cantidad > 1) {
                listaActual[index] = itemActual.copy(cantidad = itemActual.cantidad - 1)
            } else {
                listaActual.removeAt(index)
            }

            _items.value = listaActual
            calcularTotal()
        }
    }

    fun limpiarCarrito() {
        _items.value = emptyList()
        _totalCompra.value = 0
        _compraExitosa.value = false
    }

    private fun calcularTotal() {
        _totalCompra.value = _items.value.sumOf { it.total }
    }


    fun confirmarCompra(idUsuario: Long) {
        if (_items.value.isEmpty()) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val ordenRequest = OrdenRequest(
                    usuario = UsuarioId(idUsuario),
                    total = _totalCompra.value
                )
                val respOrden = repository.crearOrden(ordenRequest)

                if (respOrden.isSuccessful && respOrden.body() != null) {
                    val idOrdenGenerada = respOrden.body()!!.id
                    Log.d("Carrito", "Orden creada exitosamente con ID: $idOrdenGenerada")

                    _items.value.forEach { item ->
                        val detalle = DetalleEnvio(
                            orden = OrdenId(idOrdenGenerada),
                            producto = ProductoId(item.producto.id),
                            cantidad = item.cantidad,
                            precio = if (item.producto.enOferta && item.producto.precioOferta != null)
                                item.producto.precioOferta
                            else item.producto.precio
                        )

                        val respDetalle = repository.guardarDetalle(detalle)
                        if (!respDetalle.isSuccessful) {
                            Log.e("Carrito", "Error al guardar producto ${item.producto.nombre}")
                        }
                    }

                    _compraExitosa.value = true
                    limpiarCarrito()

                } else {
                    Log.e("Carrito", "Falló la creación de la orden principal")
                }

            } catch (e: Exception) {
                Log.e("Carrito", "Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}