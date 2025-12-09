package com.example.tufondaonline.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.tufondaonline.viewmodel.PokemonViewModel

@Composable
fun PokemonScreen(viewModel: PokemonViewModel) {
    val pokemon by viewModel.pokemonState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var intentoUsuario by remember(pokemon) { mutableStateOf("") }
    var mensajeResultado by remember(pokemon) { mutableStateOf("") }
    var esCorrecto by remember(pokemon) { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current


    fun verificarRespuesta() {
        pokemon?.let { poke ->
            if (intentoUsuario.trim().equals(poke.name, ignoreCase = true)) {
                mensajeResultado = "Adivinaste :)! Es ${poke.name.replaceFirstChar { it.uppercase() }}"
                esCorrecto = true
                keyboardController?.hide()
            } else {
                mensajeResultado = "Incorrecto, intenta de nuevo."
                esCorrecto = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "¿Quién es este Pokémon?",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier.size(300.dp, 320.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    pokemon?.let { poke ->
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(poke.sprites.fotoUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Pokemon secreto",
                            modifier = Modifier.size(200.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = if (esCorrecto) poke.name.uppercase() else "??????",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = if (esCorrecto) Color(0xFF4CAF50) else Color.Gray
                        )
                    } ?: Text("Presiona Buscar")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        if (pokemon != null && !isLoading) {
            OutlinedTextField(
                value = intentoUsuario,
                onValueChange = { intentoUsuario = it },
                label = { Text("Escribe el nombre del Pokemon...") },
                singleLine = true,
                enabled = !esCorrecto,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { verificarRespuesta() }
                ),
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { verificarRespuesta() },
                enabled = !esCorrecto && intentoUsuario.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Icon(Icons.Default.Check, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Adivinar")
            }

            if (mensajeResultado.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = mensajeResultado,
                    color = if (esCorrecto) Color(0xFF4CAF50) else Color.Red,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { viewModel.obtenerPokemonRandom() },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Icon(Icons.Default.Refresh, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (esCorrecto) "Jugar otra vez" else "Saltar / Buscar Otro")
        }
    }
}