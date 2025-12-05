package com.example.tufondaonline.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tufondaonline.R
import com.example.tufondaonline.viewmodel.UsuarioViewModel
import androidx.compose.runtime.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    viewModel: UsuarioViewModel,
    navController: NavController
){
    val isLoading by viewModel.isLoading.collectAsState()
    val loginExitoso by viewModel.loginExitoso.collectAsState()
    val regiones by viewModel.regiones.collectAsState()
    val comunas by viewModel.comunas.collectAsState()
    val usuario by viewModel.usuario.collectAsState();
    var contexto = LocalContext.current

    LaunchedEffect(true) {
        viewModel.cargarRegiones()
    }
    LaunchedEffect(loginExitoso) {
        if (loginExitoso) {
            Toast.makeText(contexto, "Registro exitoso", Toast.LENGTH_SHORT).show()

            navController.navigate("Home") {
                popUpTo("Registro") { inclusive = true }
            }
        }
    }

    LazyColumn(Modifier.fillMaxSize().padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Image(
                painter = painterResource(R.drawable.fonda),
                contentDescription = "Logo empresa",
                contentScale = ContentScale.Crop
            ) }
        item {
            Text("RUT",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 4.dp),
                color = Color.Blue)
            OutlinedTextField(
                value = usuario.rut,
                onValueChange = viewModel::onChangeRut,
                label = {Text("Ej: 12345678-k")},
                isError = usuario.errores.rut!=null,
                supportingText = {
                    usuario.errores.rut?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                }
            )
        }
        item {
            Text(
                text = "NOMBRE",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 4.dp),
                color = Color.Blue
            )
            OutlinedTextField(
                value = usuario.nombre,
                onValueChange = viewModel::onChangeNombre,
                label = {Text("Ingrese su nombre")},
                isError = usuario.errores.nombre!=null,
                supportingText = {
                    usuario.errores.nombre?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                }
            )
        }
        item {
            Text(
                text = "APELLIDO",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 4.dp),
                color = Color.Blue
            )
            OutlinedTextField(
                value = usuario.apellido,
                onValueChange = viewModel::onChangeApellido,
                label = {Text("Ingrese su apellido")},
                isError = usuario.errores.apellido!=null,
                supportingText = {
                    usuario.errores.apellido?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                }
            )
        }
        item {
            Text(
                text = "EMAIL",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 4.dp),
                color = Color.Blue
            )
            OutlinedTextField(
                value = usuario.email,
                onValueChange = viewModel::onChangeEmail,
                label = {Text("Ingrese su email")},
                isError = usuario.errores.email!=null,
                supportingText = {
                    usuario.errores.email?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                }
            )
        }
        item {
            Text(
                text = "DIRECCION",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 4.dp),
                color = Color.Blue
            )
            OutlinedTextField(
                value = usuario.direccion,
                onValueChange = viewModel::onChangeDireccion,
                label = {Text("Ingrese su direccion")},
                isError = usuario.errores.direccion!=null,
                supportingText = {
                    usuario.errores.direccion?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                }
            )
        }
        item {
            var expanded by remember { mutableStateOf(false) }
            var selectedText by remember { mutableStateOf("") }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedText,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Región") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
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
                                    selectedText = region.nombre
                                    viewModel.onChangeRegion(region.id)
                                    viewModel.cargarComunas(region.id)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
        item {
            var expandedC by remember { mutableStateOf(false) }
            var selectedComuna by remember { mutableStateOf("") }

            ExposedDropdownMenuBox(
                expanded = expandedC,
                onExpandedChange = { expandedC = !expandedC },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedComuna,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Comuna") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedC) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expandedC,
                    onDismissRequest = { expandedC = false }
                ) {
                    if (comunas.isEmpty()) {
                        DropdownMenuItem(
                            text = { Text(if (regiones.isEmpty()) "Espere..." else "Seleccione una región primero") },
                            onClick = { }
                        )
                    } else {
                        comunas.forEach { comuna ->
                            DropdownMenuItem(
                                text = { Text(comuna.nombre) },
                                onClick = {
                                    selectedComuna = comuna.nombre
                                    viewModel.onChangeComuna(comuna.id)
                                    expandedC = false
                                }
                            )
                        }
                    }
                }
            }
        }
        item {
            Text(
                text = "CONTRASEÑA",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 4.dp),
                color = Color.Blue
            )
            OutlinedTextField(
                value = usuario.password,
                onValueChange = viewModel::onChangePassword,
                label = {Text("Ingrese su contraseña")},
                isError = usuario.errores.password!=null,
                visualTransformation = PasswordVisualTransformation(),
                supportingText = {
                    usuario.errores.password?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                }
            )
        }
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = usuario.aceptarTerminos,
                    onCheckedChange = viewModel::onChangeAceptarTerminos)
                Text("Aceptar los terminos")
            }
        }
        item {
            Button(
                onClick = {
                    if (viewModel.validarRegistro()) {
                        viewModel.intentarRegistro()
                    } else {
                        Toast.makeText(contexto, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                } else {
                    Text("Registrarse")
                }
            }
        }
    }
}