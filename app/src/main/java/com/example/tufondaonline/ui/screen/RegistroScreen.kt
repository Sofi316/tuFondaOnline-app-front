package com.example.tufondaonline.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import com.example.tufondaonline.viewmodel.UsuarioRegistroViewModel

@Composable
fun RegistroScreen(
    viewModel: UsuarioRegistroViewModel,
    navController: NavController
){
    val usuario by viewModel.usuario.collectAsState();
    var contexto = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Image(
            painter = painterResource(R.drawable.fonda),
            contentDescription = "Logo empresa",
            contentScale = ContentScale.Crop
        )
        Text( //Cuadro del rut
            text = "RUT",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp),
            color = Color.Blue
        )
        OutlinedTextField( //Cuadro del rut
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
        Spacer(modifier = Modifier.height(18.dp)) //Cuadro del nombre
        Text(
            text = "NOMBRE",
            style = MaterialTheme.typography.bodyMedium, //
            modifier = Modifier.padding(bottom = 4.dp),
            color = Color.Blue
        )
        OutlinedTextField( //Cuadro del nombre
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
        Spacer(modifier = Modifier.height(18.dp)) //Cuadro del apellido
        Text(
            text = "APELLIDO",
            style = MaterialTheme.typography.bodyMedium, //
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
        Spacer(modifier = Modifier.height(18.dp)) //Cuadro del correo
        Text(
            text = "CORREO",
            style = MaterialTheme.typography.bodyMedium, //
            modifier = Modifier.padding(bottom = 4.dp),
            color = Color.Blue
        )
        OutlinedTextField(
            value = usuario.correo,
            onValueChange = viewModel::onChangeCorreo,
            label = {Text("Ingrese su correo")},
            isError = usuario.errores.correo!=null,
            supportingText = {
                usuario.errores.correo?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            }
        )
        Spacer(modifier = Modifier.height(18.dp)) //Cuadro de la direccion
        Text(
            text = "DIRECCION",
            style = MaterialTheme.typography.bodyMedium, //
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
        Spacer(modifier = Modifier.height(18.dp)) //Cuadro del password
        Text(
            text = "CONTRASEÑA",
            style = MaterialTheme.typography.bodyMedium, //
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
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = usuario.aceptarTerminos,
                onCheckedChange = viewModel::onChangeAceptarTerminos)
            Text("Aceptar los terminos")
        }
        Spacer(modifier = Modifier.height(18.dp))
        Button( //Botón ingresar
            onClick = {
                if (viewModel.validarRegistro()) {
                    val sharedPref = contexto.getSharedPreferences("usuario_prefs", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putString("correo", usuario.correo)
                        putString("password", usuario.password)
                        apply()
                    }
                    navController.navigate(route = "Login")
                    Toast.makeText(
                        contexto,
                        "Usuario registrado con éxito",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        contexto,
                        "Debe aceptar los términos de la empresa",
                        Toast.LENGTH_LONG).show()
                }
            }
        ) {
            Text("Registrarse")
        }
    }
}