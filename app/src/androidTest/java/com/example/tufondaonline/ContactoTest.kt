package com.example.tufondaonline

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.NavController
import com.example.tufondaonline.ui.screen.ContactoScreen
import com.example.tufondaonline.viewmodel.ContactoViewModel
import org.junit.Rule
import org.junit.Test

class ContactoTest {
    @get:Rule
    val composableTestRule = createComposeRule()

    @Test
    fun mostrarTituloPantalla(){
        val viewModel = ContactoViewModel()
        composableTestRule.setContent {
            ContactoScreen(
                viewModel = viewModel,
                navController = NavController(LocalContext.current)
            )
        }
        composableTestRule.onNodeWithTag("tituloContacto")
            .assertIsDisplayed()
    }

    @Test
    fun botonEnviarSeMuestra(){
        val viewModel = ContactoViewModel()
        composableTestRule.setContent {
            ContactoScreen(
                viewModel = viewModel,
                navController = NavController(LocalContext.current)
            )
        }
        composableTestRule.onNodeWithTag("btnEnviar").assertIsDisplayed()
    }
}