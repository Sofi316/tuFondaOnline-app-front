package com.example.tufondaonline.ui.screen.admin

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tufondaonline.model.Categoria
import com.example.tufondaonline.model.Producto
import com.example.tufondaonline.ui.screen.obtenerImagenLocal
import com.example.tufondaonline.viewmodel.AdminProductosViewModel

@Composable
fun AdminProductosScreen(
    viewModel: AdminProductosViewModel = viewModel()
) {
    val productos by viewModel.productos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val categorias by viewModel.categorias.collectAsState()

    Box(Modifier.fillMaxSize()) {

        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(Modifier.height(30.dp))
            Button(onClick = {
                viewModel.productoEditando = null
                viewModel.mostrarDialogo = true
            }) {
                Text("Agregar Producto")
            }

            Spacer(Modifier.height(16.dp))

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                LazyColumn {
                    items(productos) { p ->

                        MostrarProductoAdmin(
                            producto = p,
                            onEdit = {
                                viewModel.productoEditando = p
                                viewModel.mostrarDialogo = true
                            },
                            onDelete = {
                                viewModel.eliminarProducto(p.id)
                            }
                        )
                        Spacer(Modifier.height(30.dp))
                    }
                }
            }

        }

        if (viewModel.mostrarDialogo) {
            ProductoFormDialog(
                producto = viewModel.productoEditando,
                categorias= categorias,
                onDismiss = { viewModel.mostrarDialogo = false },
                onSave = { producto ->

                    if (producto.id == 0L) {
                        viewModel.crearProducto(producto)
                    } else {
                        viewModel.editarProducto(producto)
                    }

                    viewModel.mostrarDialogo = false
                }
            )
        }

    }
}



@Composable
fun MostrarProducto(
    producto: Producto
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

        }
    }
}
@Composable
fun MostrarProductoAdmin(
    producto: Producto,
    onEdit: (Producto) -> Unit,
    onDelete: (Producto) -> Unit
) {
    val contexto = LocalContext.current
    Column {

        MostrarProducto(
            producto = producto

        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(onClick = { onEdit(producto) }) {
                Icon(Icons.Default.Edit, contentDescription = null)
                Spacer(Modifier.width(6.dp))
                Text("Editar")
            }

            OutlinedButton(
                onClick = {
                    onDelete(producto)
                    Toast.makeText(
                        contexto,
                        "Producto eliminado exitósamente",
                        Toast.LENGTH_LONG).show()},
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Red
                )
            ) {
                Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                Spacer(Modifier.width(6.dp))
                Text("Eliminar")
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoFormDialog(
    producto: Producto?,
    categorias : List<Categoria>,
    onDismiss: () -> Unit,
    onSave: (Producto) -> Unit
) {
    val contexto = LocalContext.current
    var nombre by remember { mutableStateOf(producto?.nombre ?: "") }
    var descripcion by remember { mutableStateOf(producto?.descripcion ?: "") }
    var precio by remember { mutableStateOf(producto?.precio?.toString() ?: "") }
    var stock by remember { mutableStateOf(producto?.stock?.toString() ?: "") }
    var img by remember { mutableStateOf(producto?.img ?: "") }
    var enOferta by remember { mutableStateOf(producto?.enOferta ?: false) }
    var precioOferta by remember { mutableStateOf(producto?.precioOferta?.toString() ?: "") }
    var categoriaSeleccionada by remember{
        mutableStateOf(producto?.categoria?.let{cat -> categorias.find { it.id == cat.id }})
    }
    var expanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                if (categoriaSeleccionada != null) {
                    onSave(
                        Producto(
                            id = producto?.id ?: 0,
                            nombre = nombre,
                            descripcion = descripcion,
                            precio = precio.toIntOrNull() ?: 0,
                            stock = stock.toIntOrNull() ?: 0,
                            img = img,
                            enOferta = enOferta,
                            precioOferta = if (enOferta && precioOferta.isNotBlank()) precioOferta.toIntOrNull() else null,
                            categoria = categoriaSeleccionada
                        )
                    )
                } else {
                    Toast.makeText(
                        contexto,
                        "Falta seleccionar categoría",
                        Toast.LENGTH_LONG).show()
                    }
            }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        title = { Text(if (producto == null) "Agregar Producto" else "Editar Producto") },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)) {
                item{OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )}
                item{OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )}

                item{
                    OutlinedTextField(
                        value = precio,
                        onValueChange = { precio = it },
                        label = { Text("Precio") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item{
                    OutlinedTextField(
                        value = stock,
                        onValueChange = { stock = it },
                        label = { Text("Stock") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item{
                OutlinedTextField(
                    value = img,
                    onValueChange = { img = it },
                    label = { Text("URL Imagen") },
                    modifier = Modifier.fillMaxWidth()
                )}
                item{
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = enOferta,
                        onCheckedChange = { enOferta = it }
                    )
                    Text("Producto en oferta")
                }
                if (enOferta) {
                    OutlinedTextField(
                        value = precioOferta,
                        onValueChange = { precioOferta = it },
                        label = { Text("Precio oferta") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }}
                item {

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = categoriaSeleccionada?.nombre ?: "Selecciona categoría",
                            onValueChange = {},
                            label = { Text("Categoría") },
                            readOnly = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()

                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                        ) {
                            categorias.forEach { cat ->
                                DropdownMenuItem(
                                    text = { Text(cat.nombre) },
                                    onClick = {
                                        categoriaSeleccionada = cat
                                        expanded = false
                                    }
                                )
                            }

                        }

                    }


                }
            }
        }
    )
}




