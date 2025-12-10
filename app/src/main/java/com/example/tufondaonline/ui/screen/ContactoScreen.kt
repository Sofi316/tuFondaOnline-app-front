package com.example.tufondaonline.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tufondaonline.viewmodel.ContactoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ContactoScreen(
    viewModel: ContactoViewModel,
    navController: NavController
){
    var cargando by remember { mutableStateOf(false) }
    val contacto by viewModel.contacto.collectAsState();
    var contexto = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "¿Necesitas ayuda? Contáctate con nosotros",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 4.dp).testTag("tituloContacto"),
            color = Color.Blue
        )
        Spacer(modifier = Modifier.height(18.dp)) //Cuadro del asunto
        Text(
            text = "ASUNTO",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp),
            color = Color.Blue
        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(
            value = contacto.asunto,
            onValueChange = viewModel::onChangeAsunto,
            label = {Text("Reclamo, sugerencia, otro")},
            isError = contacto.erroresContacto.asunto!=null,
            supportingText = {
                contacto.erroresContacto.asunto?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            }
        )
        Spacer(modifier = Modifier.height(15.dp)) //Cuadro del mensaje
        Text(
            text = "MENSAJE",
            style = MaterialTheme.typography.bodyMedium, //
            modifier = Modifier.padding(bottom = 4.dp),
            color = Color.Blue
        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(
            value = contacto.mensaje,
            onValueChange = viewModel::onChangeMensaje,
            label = { Text("El mensaje no puede superar los 300 caracteres")},
            isError = contacto.erroresContacto.mensaje!=null,
            supportingText = {
                contacto.erroresContacto.mensaje?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth().height(180.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = {
                if(viewModel.validarContacto()){
                    cargando = true
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(3000)
                        cargando = false
                        navController.navigate(route = "Home")
                        Toast.makeText(
                            contexto,
                            "Mensaje enviado correctamente",
                            Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(
                        contexto,
                        "Por favor corrija los errores antes de enviar",
                        Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier.testTag("btnEnviar")
        ) {
            if(cargando){
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.Blue
                )
            }else{
                Text("Enviar mensaje")
            }
        }
    }
}