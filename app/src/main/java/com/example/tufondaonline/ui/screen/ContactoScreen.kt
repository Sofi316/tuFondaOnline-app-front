package com.example.tufondaonline.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tufondaonline.viewmodel.ContactoViewModel

@Composable
fun ContactoScreen(
    viewModel: ContactoViewModel,
    navController: NavController
){
    val usuario by viewModel.usuario.collectAsState();
    var contexto = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "¿Necesitas ayuda? Contáctate con nosotros",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp),
            color = Color.Blue
        )
        Spacer(modifier = Modifier.height(18.dp)) //Cuadro del nombre
        Text(
            text = "NOMBRE",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp),
            color = Color.Blue
        )
        Spacer(modifier = Modifier.height(18.dp))
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
        Spacer(modifier = Modifier.height(18.dp)) //Cuadro del correo
        Text(
            text = "CORREO",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp),
            color = Color.Blue
        )
        Spacer(modifier = Modifier.height(18.dp))
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
        Spacer(modifier = Modifier.height(18.dp)) //Cuadro del mensaje
        Text(
            text = "MENSAJE",
            style = MaterialTheme.typography.bodyMedium, //
            modifier = Modifier.padding(bottom = 4.dp),
            color = Color.Blue
        )
        Spacer(modifier = Modifier.height(18.dp))
        OutlinedTextField(
            value = usuario.mensaje,
            onValueChange = viewModel::onChangeMensaje,
            label = {Text("Ingrese su mensaje")},
            isError = usuario.errores.mensaje!=null,
            supportingText = {
                usuario.errores.mensaje?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            }
        )
        Button(
            onClick = {
                if(viewModel.validarContacto()){
                    Toast.makeText(
                        contexto,
                        "Mensaje enviado correctamente",
                        Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(
                        contexto,
                        "Por favor corrija los errores antes de enviar",
                        Toast.LENGTH_LONG).show()
                }
            }
        ) {
            Text("Enviar Mensaje")
        }
    }
}