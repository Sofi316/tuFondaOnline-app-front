
package com.example.tufondaonline.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tufondaonline.ui.theme.darkBlue
import com.example.tufondaonline.ui.theme.lightBlue
import kotlinx.coroutines.launch
import com.example.tufondaonline.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar() {


    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf("Inicio") }
    val navItems = listOf("Inicio", "Productos", "Ofertas", "Blogs", "Nosotros", "Contacto")


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.darkBlue
            ) {

                // 1. Tu Logo/Título
                Image(
                    painter = painterResource(id = R.drawable.fonda),
                    contentDescription = "Logo de Fonda Online",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp, horizontal = 16.dp),
                    contentScale = ContentScale.Fit
                )
                HorizontalDivider(color = Color.White)
                Spacer(modifier = Modifier.height(12.dp))


                navItems.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(text = item) },
                        selected = item == selectedItem,
                        onClick = {
                            selectedItem = item
                            scope.launch { drawerState.close() }
                            // Aquí irá la navegación
                        },
                        colors= NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = MaterialTheme.colorScheme.lightBlue,
                            unselectedContainerColor = Color.Transparent,
                            unselectedTextColor = Color.White,

                        )
                    )
                }

            }
        }
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.darkBlue,
                        titleContentColor = Color.White
                    ),

                    title = { Text(text = "Fonda Online") },


                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir menú",
                                tint = Color.White)
                        }
                    }

                )
            }
        ) { paddingValues ->

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