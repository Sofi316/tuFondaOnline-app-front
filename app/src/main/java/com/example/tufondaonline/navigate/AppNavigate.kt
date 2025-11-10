package com.example.tufondaonline.navigate

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tufondaonline.ui.screen.*
import com.example.tufondaonline.viewmodel.ContactoViewModel
import com.example.tufondaonline.viewmodel.UsuarioViewModel

@Composable
fun AppNavigate(){
    val navController = rememberNavController()
    val usuarioViewModel: UsuarioViewModel= viewModel()
    val contactoViewModel: ContactoViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "Login"
    )
    {
        composable(route = "Login") {
            LoginScreen(usuarioViewModel, navController)
        }
        composable(route = "Registro"){
            RegistroScreen(usuarioViewModel,navController)
        }

        composable(route = "Productos"){
            MainScreen(navController = navController) {
                CategoriaScreen(navController = navController)
            }
        }
        composable(route = "Ofertas"){
            MainScreen(navController = navController) {
                OfertasScreen()
            }
        }

        composable(
            route = "Productos/{categoria}",
            arguments = listOf(navArgument("categoria") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoria = backStackEntry.arguments?.getString("categoria") ?: ""
            MainScreen(navController = navController) {
                ProductoScreen(categoria = categoria)
            }
        }

        composable(route = "Home"){
            MainScreen(navController = navController) {
                HomeScreen()
            }
        }

        composable(route = "Nosotros"){
            MainScreen(navController = navController) {
                NosotrosScreen()
            }
        }
        composable(route = "Contacto"){
            MainScreen(navController = navController) {
                ContactoScreen(contactoViewModel,navController)
            }
        }

        composable(route = "Perfil"){
            MainScreen(navController = navController) {
                PerfilScreen(usuarioViewModel,navController)
            }
        }
        composable(route = "Carrito"){
            MainScreen(navController = navController) {
                CarritoScreen()
            }
        }
    }
}