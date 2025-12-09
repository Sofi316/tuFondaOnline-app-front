package com.example.tufondaonline

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import com.example.tufondaonline.ui.screen.CardBienvenida
import com.example.tufondaonline.ui.screen.CardConImagenDeFondo
import org.junit.Rule
import org.junit.Test

class BienvenidaTest {
    @get:Rule
    val composableTest = createComposeRule()

    @Test
    fun bienvenida_se_muestra() {
        composableTest.setContent {
            CardBienvenida()
        }
        composableTest.onNodeWithTag("bienvenida").assertExists()
    }

    @Test
    fun card_con_imagen_se_muestra() {
        composableTest.setContent {
            CardConImagenDeFondo()
        }
        composableTest
            .onNodeWithContentDescription("Fonda")
            .assertIsDisplayed()
        composableTest
            .onNodeWithTag("recordatorio")
            .assertIsDisplayed()
    }
}


