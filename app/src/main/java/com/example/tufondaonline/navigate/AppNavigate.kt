package com.example.tufondaonline.navigate

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tufondaonline.ui.screen.ContactoScreen
import com.example.tufondaonline.ui.screen.HomeScreen
import com.example.tufondaonline.ui.screen.RegistroScreen
import com.example.tufondaonline.ui.screen.LoginScreen
import com.example.tufondaonline.ui.screen.MainScreen
import com.example.tufondaonline.ui.screen.ProductoScreen
import com.example.tufondaonline.viewmodel.ContactoViewModel
import com.example.tufondaonline.viewmodel.UsuarioRegistroViewModel
import com.example.tufondaonline.viewmodel.UsuarioViewModel

@Composable
fun AppNavigate(){
    val navController = rememberNavController()
    val usuarioViewModel: UsuarioViewModel= viewModel()
    val usuarioRegistroViewModel: UsuarioRegistroViewModel = viewModel()
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
            RegistroScreen(usuarioRegistroViewModel,navController)
        }
        composable(route = "Productos"){
            MainScreen(navController = navController) {
                ProductoScreen()
            }
        }
        composable(route = "Home"){
            MainScreen(navController = navController) {
                HomeScreen()
            }
        }
        composable(route = "Contacto"){
            MainScreen(navController = navController) {
                ContactoScreen(contactoViewModel,navController)
            }
        }
    }
}