package com.example.tufondaonline.ui.screen

import android.Manifest
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tufondaonline.utils.obtenerImagenLocal
import com.example.tufondaonline.viewmodel.CarritoViewModel
import com.example.tufondaonline.viewmodel.UsuarioViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import kotlinx.coroutines.tasks.await

@SuppressLint("MissingPermission")
@Composable
fun CarritoScreen(
    carritoViewModel: CarritoViewModel,
    usuarioViewModel: UsuarioViewModel,
    navController: NavController
) {
    val items by carritoViewModel.items.collectAsState()
    val total by carritoViewModel.totalCompra.collectAsState()
    val compraExitosa by carritoViewModel.compraExitosa.collectAsState()
    val isLoading by carritoViewModel.isLoading.collectAsState()
    val usuario by usuarioViewModel.usuario.collectAsState()

    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var userLocation by remember { mutableStateOf<Pair<Double, Double>?>(null) }
    var locationMessage by remember { mutableStateOf("Solicitando ubicación...") }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                locationMessage = "Permiso concedido. Buscando señal..."
            } else {
                locationMessage = "Permiso denegado. No se podrá guardar la ubicación."
            }
        }
    )

    suspend fun recuperarUbicacion() {
        try {
            val location = fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY, null
            ).await()
            if (location != null) {
                userLocation = Pair(location.latitude, location.longitude)
                locationMessage = "Ubicación de entrega detectada"
            } else {
                locationMessage = "No se pudo obtener ubicación exacta."
            }
        } catch (e: Exception) {
            locationMessage = "Error en GPS."
        }
    }

    LaunchedEffect(Unit) {
        locationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    LaunchedEffect(locationMessage) {
        if (locationMessage.contains("Permiso concedido")) {
            recuperarUbicacion()
        }
    }

    LaunchedEffect(compraExitosa) {
        if (compraExitosa) {
            Toast.makeText(context, "Pedido enviado con éxito", Toast.LENGTH_LONG).show()
            carritoViewModel.limpiarCarrito()
            navController.navigate("Home")
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text("Tu Carrito", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        if (items.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("El carrito está vacío", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items) { item ->
                    Card(elevation = CardDefaults.cardElevation(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val imagenId = obtenerImagenLocal(item.producto.img ?: "", context)

                            Image(
                                painter = painterResource(imagenId),
                                contentDescription = null,
                                modifier = Modifier.size(60.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(item.producto.nombre, fontWeight = FontWeight.Bold)
                                Text("${item.cantidad} x $${item.producto.precio}")
                                Text("Total: $${item.total}", color = MaterialTheme.colorScheme.primary)
                            }
                            IconButton(onClick = { carritoViewModel.agregarProducto(item.producto) }) {
                                Icon(Icons.Default.Add, contentDescription = "Sumar")
                            }
                            IconButton(onClick = { carritoViewModel.quitarProducto(item.producto) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Restar", tint = Color.Red)
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Confirmar Ubicación de Entrega", style = MaterialTheme.typography.titleMedium)
                            Text(locationMessage, style = MaterialTheme.typography.bodySmall)

                            Spacer(modifier = Modifier.height(8.dp))

                            if (userLocation != null) {
                                val (lat, lng) = userLocation!!

                                MapboxMap(
                                    Modifier
                                        .fillMaxWidth()
                                        .height(250.dp),
                                    mapViewportState = rememberMapViewportState {
                                        setCameraOptions {
                                            zoom(15.0)
                                            center(Point.fromLngLat(lng, lat))
                                            pitch(0.0)
                                            bearing(0.0)
                                        }
                                    },
                                ) {
                                    PointAnnotation(point = Point.fromLngLat(lng, lat))
                                }
                                Text("Lat: $lat, Lng: $lng", style = MaterialTheme.typography.labelSmall)
                            } else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("TOTAL A PAGAR:", style = MaterialTheme.typography.titleLarge)
                                Text("$$total", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    if (usuario.id != 0L) {
                                        carritoViewModel.confirmarCompra(usuario.id)
                                        Toast.makeText(context, "Compra confirmada", Toast.LENGTH_LONG).show()
                                    } else {
                                        Toast.makeText(context, "Debes iniciar sesión", Toast.LENGTH_SHORT).show()
                                        navController.navigate("Login")
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = !isLoading
                            ) {
                                if (isLoading) {
                                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                                } else {
                                    Text("CONFIRMAR COMPRA")
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

