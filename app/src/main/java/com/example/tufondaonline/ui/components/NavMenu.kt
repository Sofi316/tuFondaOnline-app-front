
package com.ejemplo.tufondaonline.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar() { // Renombrado para más claridad

    // --- ESTADO ---
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf("Inicio") }
    val navItems = listOf("Inicio", "Categorías", "Ofertas", "Blogs", "Nosotros", "Contacto")

    // --- MENÚ LATERAL (ModalNavigationDrawer) ---
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // 'ModalDrawerSheet' es el contenedor blanco del menú
            ModalDrawerSheet {

                // 1. Tu Logo/Título
                Text(
                    "Fonda Online",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
                HorizontalDivider()

                // 2. Mapeamos tu lista de enlaces (SOLO NAVEGACIÓN)
                navItems.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(text = item) },
                        selected = item == selectedItem,
                        onClick = {
                            selectedItem = item
                            scope.launch { drawerState.close() }
                            // Aquí irá la navegación
                        }
                    )
                }
                // (Ya no están los botones de Sesión aquí)
            }
        }
    ) {
        // --- CONTENIDO DE LA PANTALLA (Scaffold) ---
        Scaffold(
            topBar = {
                TopAppBar(
                    // 3. Título (o logo)
                    title = { Text(text = "Fonda Online") },

                    // 4. ICONO DE HAMBURGUESA (para abrir el drawer)
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir menú")
                        }
                    },

                    // 5. ACCIONES A LA DERECHA (Botón Iniciar Sesión)
                    actions = {
                        TextButton(
                            onClick = {
                                // Aquí pones la lógica para navegar a la
                                // pantalla de IniciarSesión
                            }
                        ) {
                            Text("Iniciar Sesión")
                        }
                    }
                )
            }
        ) { paddingValues ->
            // 6. CONTENIDO DE TU PANTALLA
            // (Aquí se mostrará la pantalla de Inicio, Nosotros, etc.)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text(text = "Contenido de la pantalla: $selectedItem")
            }
        }
    }
}