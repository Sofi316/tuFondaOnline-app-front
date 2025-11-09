package com.example.tufondaonline.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tufondaonline.R
import com.example.tufondaonline.ui.theme.darkBlue
import com.example.tufondaonline.ui.theme.lightBlue
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar(navController: NavController, content: @Composable () -> Unit) {


    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val navItems = listOf("Inicio", "Productos", "Ofertas", "Nosotros", "Contacto")


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.darkBlue
            ) {

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
                    val selectionRoute = when (item) {
                        "Inicio" -> "Home"
                        "Productos" -> "Productos"
                        else -> item
                    }

                    NavigationDrawerItem(
                        label = { Text(text = item) },
                        selected = selectionRoute == currentRoute,
                        onClick = {
                            scope.launch { drawerState.close() }
                            val navRoute = when (item) {
                                "Inicio"->"Home"
                                "Productos" -> "Productos"
                                "Ofertas" ->"Ofertas"
                                else -> "Home"
                            }
                            navController.navigate(navRoute) {
                                launchSingleTop = true
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                            }
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = MaterialTheme.colorScheme.lightBlue,
                            unselectedContainerColor = Color.Transparent,
                            selectedTextColor = Color.White,
                            unselectedTextColor = Color.White
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
                    },
                    actions = {
                        IconButton(onClick={ navController.navigate("Carrito")}){
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Carrito de compras",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick={ navController.navigate("Perfil")}){
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Perfil de usuario",
                                tint = Color.White
                            )
                        }

                    }


                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                content()
            }
        }
    }
}
