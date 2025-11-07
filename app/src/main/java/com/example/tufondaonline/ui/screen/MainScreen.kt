package com.example.tufondaonline.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.tufondaonline.ui.components.NavBar

@Composable
fun MainScreen(navController: NavController, content: @Composable () -> Unit) {
    NavBar(navController = navController) {
        content()
    }
}