package com.example.tufondaonline.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tufondaonline.viewmodel.UsuarioViewModel
import com.example.tufondaonline.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.nio.file.WatchEvent

@Composable
fun LoginScreen(
    viewModel: UsuarioViewModel,
    navController: NavController
){
    var cargando by remember { mutableStateOf(false) }
    val usuario by viewModel.usuario.collectAsState();
    var contexto = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Image( //Imagen de la empresa
            painter = painterResource(R.drawable.fonda),
            contentDescription = "Logo empresa",
            contentScale = ContentScale.Crop,
        )
        OutlinedTextField(
            value = usuario.correo,
            onValueChange = viewModel::onChangeCorreo,
            label = {Text("Correo")},
            isError = usuario.errores.correo!=null,
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Icono correo") },
            supportingText = {
                usuario.errores.correo?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            }
        )
        Spacer(modifier = Modifier.height(18.dp)) //Cuadro del password
        OutlinedTextField(
            value = usuario.password,
            onValueChange = viewModel::onChangePassword,
            label = {Text("Contraseña")},
            isError = usuario.errores.password!=null,
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = {Icon(Icons.Default.Lock, contentDescription = "Icono contraseña")},
            supportingText = {
                usuario.errores.password?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            }
        )
        Spacer(modifier = Modifier.height(18.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,

        ) {
            Button(
                onClick = {
                    if (viewModel.validarLogin()){
                        cargando = true
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(3000)
                            cargando= false
                            val sharedPref = contexto.getSharedPreferences("usuario_prefs", Context.MODE_PRIVATE)
                            val correoGuardado = sharedPref.getString("correo", null)
                            val passwordGuardada = sharedPref.getString("password", null)
                            if (usuario.email== correoGuardado && usuario.password == passwordGuardada) {
                                val rutGuardado = sharedPref.getString("rut", "") ?: ""
                                val nombreGuardado = sharedPref.getString("nombre", "") ?: ""
                                val apellidoGuardado = sharedPref.getString("apellido", "") ?: ""
                                val direccionGuardada = sharedPref.getString("direccion", "") ?: ""

                                viewModel.cargarUsuarioCompleto(
                                    rut = rutGuardado,
                                    nombre = nombreGuardado,
                                    apellido = apellidoGuardado,
                                    email= correoGuardado!!, // Sabemos que no es nulo por la comprobación.
                                    direccion = direccionGuardada,
                                    password = passwordGuardada!!
                                )

                                navController.navigate("Home")
                                Toast.makeText(
                                    contexto,
                                    "Ingreso exitoso",
                                    Toast.LENGTH_SHORT).show()
                            }
                            if (usuario.correo!= correoGuardado || usuario.password!= passwordGuardada){
                                Toast.makeText(
                                    contexto,
                                    "Emailo contraseña incorrectos",
                                    Toast.LENGTH_LONG).show()
                            }
                        }
                } else {
                    Toast.makeText(contexto, "Complete los espacios vacíos por favor", Toast.LENGTH_LONG).show()
                }
            }) {
                if (cargando){
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.Blue
                    )
                }else{
                    Text("Ingresar")
                }
            }
            Button(
                onClick = {
                    navController.navigate(route = "Registro")
                }
            ) {
                Text("Registrarse")
            }
        }

    }

}