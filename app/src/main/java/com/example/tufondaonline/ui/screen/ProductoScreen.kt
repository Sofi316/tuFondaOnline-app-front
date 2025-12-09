package com.example.tufondaonline.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tufondaonline.model.Producto
import com.example.tufondaonline.viewmodel.CarritoViewModel
import com.example.tufondaonline.viewmodel.ProductoViewModel

@Composable
fun MostrarProducto(
    producto: Producto,
    carritoViewModel: CarritoViewModel
){
    var expandido by remember { mutableStateOf(false) }
    val contexto = LocalContext.current

    val imagenId = obtenerImagenLocal(producto.img ?: "", contexto)

    Card(
        modifier = Modifier.padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ){
        Column(
            modifier = Modifier.animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        ){
            Image(
                painter = painterResource(id = imagenId),
                contentDescription = "Foto comida",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (expandido) 300.dp else 194.dp)
                    .clickable { expandido = !expandido }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (producto.enOferta && producto.precioOferta != null) {
                        Text("$${producto.precio}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            textDecoration = TextDecoration.LineThrough,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = "$${producto.precioOferta}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    } else {
                        Text(
                            text = "$${producto.precio}",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
            }

            Button(
                onClick = {
                    carritoViewModel.agregarProducto(producto)
                    Toast.makeText(contexto, "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Agregar al carrito")
            }

            if (expandido) {
                Text(
                    text = producto.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun ProductoScreen(
    categoria: String,
    carritoViewModel: CarritoViewModel,
    productoViewModel: ProductoViewModel = viewModel()
) {
    val listaProductos by productoViewModel.productos.collectAsState()

    val productosFiltrados = remember(categoria, listaProductos) {
        if (categoria == "Todos"){
            listaProductos
        } else {
            listaProductos.filter { it.categoria?.nombre == categoria }
        }
    }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(productosFiltrados) { producto ->
                MostrarProducto(producto, carritoViewModel)
            }
        }
    }
}

fun obtenerImagenLocal(nombreImagen: String, context: Context): Int {
    val nombreLimpio = nombreImagen.lowercase().trim()
    val id = context.resources.getIdentifier(nombreLimpio, "drawable", context.packageName)
    return if (id != 0) id else android.R.drawable.ic_menu_report_image
}