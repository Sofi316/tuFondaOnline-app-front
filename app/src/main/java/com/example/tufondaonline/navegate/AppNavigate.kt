package com.example.tufondaonline.navegate

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tufondaonline.ui.screen.LoginScreen
import com.example.tufondaonline.viewmodel.UsuarioViewModel

@Composable
fun AppNavigate(){
    val navController = rememberNavController()
    val usuarioViewModel: UsuarioViewModel= viewModel()

    NavHost(
        navController = navController,
        startDestination = "Login"
    )
    {
        composable(route = "Login") {
            LoginScreen(usuarioViewModel, navController)
        }
    }
}