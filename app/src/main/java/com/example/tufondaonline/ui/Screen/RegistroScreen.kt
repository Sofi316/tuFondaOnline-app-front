package com.example.tufondaonline.ui.Screen

import android.text.Html
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tufondaonline.viewmodel.UsuarioViewModel
import com.example.tufondaonline.R

@Composable
fun RegistroScreen(
    viewModel: UsuarioViewModel,
    navController: NavController
){
    val usuario by viewModel.usuario.collectAsState();
    var contexto = LocalContext.current

    Column (
        modifier = Modifier.fillMaxSize().padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(R.drawable.fondasom),
            contentDescription = "Logo empresa",
            contentScale = ContentScale.Crop
        )
        OutlinedTextField( //Cuadro del nombre
            value = usuario.nombre,
            onValueChange = viewModel::onChangeNombre,
            label = {Text("Nombre")},
            isError = usuario.errores.nombre!=null,
            supportingText = {
                usuario.errores.nombre?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            }
        )
        Spacer(modifier = Modifier.height(18.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = usuario.aceptarTerminos,
                onCheckedChange = viewModel::onChangeAceptarTerminos)
            Text("Aceptar los terminos")
        }
    }
}