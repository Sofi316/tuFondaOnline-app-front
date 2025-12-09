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
import com.example.tufondaonline.viewmodel.PokemonViewModel
import androidx.compose.runtime.remember
import com.example.tufondaonline.ui.screen.admin.AdminHomeScreen
import com.example.tufondaonline.ui.screen.admin.AdminOrdenesScreen
import com.example.tufondaonline.ui.screen.admin.AdminProductosScreen
import com.example.tufondaonline.ui.screen.admin.AdminUsuariosScreen
import com.example.tufondaonline.viewmodel.AdminProductosViewModel
import com.example.tufondaonline.viewmodel.AdminUsuarioViewModel
import com.example.tufondaonline.viewmodel.AdminViewModel
import com.example.tufondaonline.viewmodel.CarritoViewModel

@Composable
fun AppNavigate(){
    val navController = rememberNavController()
    val usuarioViewModel: UsuarioViewModel= viewModel()
    val contactoViewModel: ContactoViewModel = viewModel()
    val carritoViewModel: CarritoViewModel = viewModel()
    val adminViewModel: AdminViewModel = viewModel()
    val adminProductosViewModel: AdminProductosViewModel = viewModel()
    val adminUsuarioViewModel: AdminUsuarioViewModel = viewModel()


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
                OfertasScreen(carritoViewModel=carritoViewModel)
            }
        }

        composable(
            route = "Productos/{categoria}",
            arguments = listOf(navArgument("categoria") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoria = backStackEntry.arguments?.getString("categoria") ?: ""
            MainScreen(navController = navController) {
                ProductoScreen(categoria = categoria,carritoViewModel=carritoViewModel)
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
                CarritoScreen(
                    carritoViewModel = carritoViewModel,
                    usuarioViewModel = usuarioViewModel,
                    navController = navController)
            }
        }
        composable(route = "Pokemon"){
            val pokeViewModel = remember { PokemonViewModel() }
            MainScreen(navController = navController) {
                PokemonScreen(viewModel = pokeViewModel)
            }
        }
        composable(route = "AdminHome"){
                AdminHomeScreen(adminViewModel,navController)
        }
        composable(route = "AdminOrdenes"){
            AdminOrdenesScreen(adminViewModel)
        }
        composable(route = "AdminProductos"){
            AdminProductosScreen(adminProductosViewModel)
        }
        composable(route= "AdminUsuarios") {
            AdminUsuariosScreen(adminUsuarioViewModel,usuarioViewModel)
        }
    }
}