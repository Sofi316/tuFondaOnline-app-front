package com.example.tufondaonline.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tufondaonline.viewmodel.CarritoViewModel
import com.example.tufondaonline.viewmodel.ProductoViewModel

@Composable
fun OfertasScreen(
    carritoViewModel: CarritoViewModel,
    productoViewModel: ProductoViewModel = viewModel()
){
    val listaProductos by productoViewModel.productos.collectAsState()
    val isLoading by productoViewModel.isLoading.collectAsState()

    val productosEnOferta = remember(listaProductos) {
        listaProductos.filter { it.enOferta }
    }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (productosEnOferta.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No hay ofertas disponibles por el momento", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(productosEnOferta) { producto ->
                    MostrarProducto(producto, carritoViewModel)
                }
            }
        }
    }
}