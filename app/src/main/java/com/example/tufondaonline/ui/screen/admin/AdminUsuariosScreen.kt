package com.example.tufondaonline.ui.screen.admin

import android.widget.Toast
import androidx.compose.material3.*
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tufondaonline.model.Comuna
import com.example.tufondaonline.model.Usuarios
import com.example.tufondaonline.viewmodel.AdminUsuarioViewModel
import com.example.tufondaonline.viewmodel.UsuarioViewModel

@Composable
fun AdminUsuariosScreen(
    viewModel: AdminUsuarioViewModel = viewModel(),
    usuarioViewModel: UsuarioViewModel = viewModel()
) {
    val usuarios by viewModel.usuarios.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val comunas by usuarioViewModel.comunas.collectAsState()

    LaunchedEffect(true) {
        usuarioViewModel.cargarRegiones()
    }

    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(Modifier.height(30.dp))

            Button(onClick = {
                viewModel.usuarioEditando = null
                usuarioViewModel.onChangeRegion(0L)
                usuarioViewModel.onChangeComuna(0L)
                viewModel.mostrarDialogo = true
            }) {
                Text("Agregar Usuario")
            }

            Spacer(Modifier.height(16.dp))

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                LazyColumn {
                    items(usuarios) { u ->
                        MostrarUsuario(
                            usuario = u,
                            onEdit = {
                                viewModel.usuarioEditando = u

                                u.comuna?.let { comuna ->
                                    comuna.region?.id?.let { regionId ->
                                        usuarioViewModel.onChangeRegion(regionId)
                                    }
                                    usuarioViewModel.onChangeComuna(comuna.id)
                                }
                                viewModel.mostrarDialogo = true
                            },
                            onDelete = {
                                viewModel.eliminarUsuario(u.id)
                            }
                        )
                    }
                }
            }
        }

        if (viewModel.mostrarDialogo) {
            UsuarioFormDialog(
                usuario = viewModel.usuarioEditando,
                comunas = comunas,
                viewModel = usuarioViewModel,
                onDismiss = {
                    viewModel.mostrarDialogo = false
                    // Limpiar selecciones al cerrar
                    usuarioViewModel.onChangeRegion(0L)
                    usuarioViewModel.onChangeComuna(0L)
                },
                onSave = { usuario ->
                    if (usuario.id == 0L) {
                        viewModel.crearUsuario(usuario)
                    } else {
                        viewModel.editarUsuario(usuario)
                    }
                    viewModel.mostrarDialogo = false
                    // Limpiar selecciones después de guardar
                    usuarioViewModel.onChangeRegion(0L)
                    usuarioViewModel.onChangeComuna(0L)
                }
            )
        }
    }
}

@Composable
fun MostrarUsuario(
    usuario: Usuarios,
    onEdit: (Usuarios) -> Unit,
    onDelete: (Usuarios) -> Unit
) {
    val contexto = LocalContext.current

    Card(
        modifier = Modifier.padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Id", style = MaterialTheme.typography.titleMedium)
                Text(text = usuario.id.toString(), style = MaterialTheme.typography.bodyMedium)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Nombre", style = MaterialTheme.typography.titleMedium)
                Text(text = usuario.nombre, style = MaterialTheme.typography.bodyMedium)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Correo", style = MaterialTheme.typography.titleMedium)
                Text(text = usuario.email, style = MaterialTheme.typography.bodyMedium)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Rut", style = MaterialTheme.typography.titleMedium)
                Text(text = usuario.rut, style = MaterialTheme.typography.bodyMedium)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Dirección", style = MaterialTheme.typography.titleMedium)
                Text(text = usuario.direccion, style = MaterialTheme.typography.bodyMedium)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Comuna", style = MaterialTheme.typography.titleMedium)
                Text(text = usuario.comuna?.nombre ?: "-", style = MaterialTheme.typography.bodyMedium)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Rol", style = MaterialTheme.typography.titleMedium)
                Text(text = usuario.rol, style = MaterialTheme.typography.bodyMedium)
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(onClick = { onEdit(usuario) }) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                    Spacer(Modifier.width(6.dp))
                    Text("Editar")
                }

                OutlinedButton(
                    onClick = {
                        onDelete(usuario)
                        Toast.makeText(contexto, "Usuario eliminado exitosamente", Toast.LENGTH_LONG).show()
                    },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                    Spacer(Modifier.width(6.dp))
                    Text("Eliminar")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsuarioFormDialog(
    usuario: Usuarios?,
    viewModel: UsuarioViewModel,
    comunas: List<Comuna>,
    onDismiss: () -> Unit,
    onSave: (Usuarios) -> Unit
) {
    val contexto = LocalContext.current
    val regiones by viewModel.regiones.collectAsState()
    val regionSeleccionada by viewModel.regionSeleccionada.collectAsState()
    val comunaSeleccionada by viewModel.comunaSeleccionada.collectAsState()

    var expandedRegion by remember { mutableStateOf(false) }
    var expandedComuna by remember { mutableStateOf(false) }

    var nombre by remember { mutableStateOf(usuario?.nombre ?: "") }
    var email by remember { mutableStateOf(usuario?.email ?: "") }
    var rut by remember { mutableStateOf(usuario?.rut ?: "") }
    var direccion by remember { mutableStateOf(usuario?.direccion ?: "") }
    var rol by remember { mutableStateOf(usuario?.rol ?: "") }

    LaunchedEffect(usuario) {
        if (usuario != null) {
            nombre = usuario.nombre
            email = usuario.email
            rut = usuario.rut
            direccion = usuario.direccion
            rol = usuario.rol

            usuario.comuna?.let { comuna ->
                comuna.region?.id?.let { regionId ->
                    viewModel.onChangeRegion(regionId)
                }
                kotlinx.coroutines.delay(100)
                viewModel.onChangeComuna(comuna.id)
            }
        } else {
            viewModel.onChangeRegion(0L)
            viewModel.onChangeComuna(0L)
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                if (rol == "ADMINISTRADOR" || rol == "CLIENTE") {

                    val comunaFinal = comunaSeleccionada

                    if (comunaFinal != null) {
                        onSave(
                            Usuarios(
                                id = usuario?.id ?: 0,
                                nombre = nombre,
                                email = email,
                                rut = rut,
                                direccion = direccion,
                                comuna = comunaFinal,
                                rol = rol
                            )
                        )
                    } else {
                        Toast.makeText(
                            contexto,
                            "Debe seleccionar una comuna",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        contexto,
                        "Los roles solo pueden ser ADMINISTRADOR o CLIENTE",
                        Toast.LENGTH_LONG
                    ).show()
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
        title = { Text(if (usuario == null) "Agregar Usuario" else "Editar Usuario") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = rut,
                    onValueChange = { rut = it },
                    label = { Text("RUT") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = direccion,
                    onValueChange = { direccion = it },
                    label = { Text("Dirección") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = rol,
                    onValueChange = { rol = it },
                    label = { Text("Rol") },
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenuBox(
                    expanded = expandedRegion,
                    onExpandedChange = { expandedRegion = !expandedRegion },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = regionSeleccionada?.nombre ?: "Seleccione Región",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Región") },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedRegion,
                        onDismissRequest = { expandedRegion = false }
                    ) {
                        if (regiones.isEmpty()) {
                            DropdownMenuItem(
                                text = { Text("Cargando regiones...") },
                                onClick = { }
                            )
                        } else {
                            regiones.forEach { region ->
                                DropdownMenuItem(
                                    text = { Text(region.nombre) },
                                    onClick = {
                                        viewModel.onChangeRegion(region.id)
                                        viewModel.onChangeComuna(0L) // Limpiar comuna al cambiar región
                                        expandedRegion = false
                                    }
                                )
                            }
                        }
                    }
                }

                ExposedDropdownMenuBox(
                    expanded = expandedComuna,
                    onExpandedChange = { expandedComuna = !expandedComuna },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = comunaSeleccionada?.nombre ?: "Seleccione Comuna",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Comuna") },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedComuna,
                        onDismissRequest = { expandedComuna = false }
                    ) {
                        if (comunas.isEmpty()) {
                            DropdownMenuItem(
                                text = { Text(if (regionSeleccionada == null) "Seleccione una región primero" else "Cargando comunas...") },
                                onClick = {}
                            )
                        } else {
                            comunas.forEach { comuna ->
                                DropdownMenuItem(
                                    text = { Text(comuna.nombre) },
                                    onClick = {
                                        viewModel.onChangeComuna(comuna.id)
                                        expandedComuna = false
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