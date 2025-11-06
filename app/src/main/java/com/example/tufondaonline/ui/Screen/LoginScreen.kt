package com.example.tufondaonline.ui.screen

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
import java.nio.file.WatchEvent

@Composable
fun LoginScreen(
    viewModel: UsuarioViewModel,
    navController: NavController
){
    val usuario by viewModel.usuario.collectAsState();
    var contexto = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image( //Imagen de la empresa
            painter = painterResource(R.drawable.fonda),
            contentDescription = "Logo empresa",
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(18.dp)) //Cuadro del correo
        OutlinedTextField(
            value = usuario.correo,
            onValueChange = viewModel::onChangeCorreo,
            label = {Text("Correo")},
            isError = usuario.errores.correo!=null,
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
            verticalAlignment = Alignment.CenterVertically, // Alineación vertical

        ) {
            Button( //Botón ingresar
                onClick = {
                    if (viewModel.validarLogin()) {
                        navController.navigate(route = "Bienvenida")
                    } else {
                        Toast.makeText(
                            contexto,
                            "Debe aceptar los términos de la empresa",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            ) {
                Text("Ingresar")
            }
            // Botón de Registro
            Button(
                onClick = {
                    // Navegar a la pantalla de registro
                    navController.navigate(route = "RegistroUsuario")
                }
            ) {
                Text("Registrarse")
            }
        }

    }

}