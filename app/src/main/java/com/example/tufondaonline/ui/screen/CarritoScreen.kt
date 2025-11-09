package com.example.tufondaonline.ui.screen

import android.Manifest
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import kotlinx.coroutines.tasks.await
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager

@Composable
fun CarritoScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Carrito vacío", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        ConseguirGeolocalizacion()
    }
}

@SuppressLint("MissingPermission")
@Composable
fun ConseguirGeolocalizacion(){
    val contexto = LocalContext.current
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(contexto)
    }
    var userLocation by remember{ mutableStateOf<Pair<Double, Double>?>(null) }
    var locationMessage by remember { mutableStateOf("Solicitando permiso de ubicación...") }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true){
                locationMessage = "Permiso concedido. Recuperando ubicación..."
            } else {
                locationMessage = "Permiso denegado."
            }
        }
    )

    suspend fun recuperarCurrentLocation() {
        try {
            val location = fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY, null
            ).await()
            if (location != null) {
                userLocation = Pair(location.latitude, location.longitude)
                locationMessage = "Su ubicación actual es:"
            } else {
                locationMessage = "No se pudo recuperar la ubicación."
            }
        } catch (e: Exception) {
            locationMessage = "Error al recuperar ubicación."
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
            recuperarCurrentLocation()
        }
    }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(locationMessage, style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(10.dp))
        }
        userLocation?.let { (latitude, longitude) ->
            item {
                Text("Latitud: $latitude")
                Text("Longitud: $longitude")
                Spacer(Modifier.height(10.dp))
            }
            item {
                MapboxMap(
                    Modifier.fillMaxWidth().height(300.dp),
                    mapViewportState = rememberMapViewportState {
                        setCameraOptions {
                            zoom(18.0)
                            center(Point.fromLngLat(longitude, latitude))
                            pitch(0.0)
                            bearing(0.0)
                        }
                    },
                ) {
                    PointAnnotation(
                        point = Point.fromLngLat(longitude, latitude)
                    )
                }
            }
            item {
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = {
                        Toast.makeText(contexto, "Ubicacion confirmada", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Text("Confirmar ubicación")
                }
            }
        }
        }
    }


