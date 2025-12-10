package com.example.tufondaonline

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.example.tufondaonline.ui.screen.NuestraMisionCard
import com.example.tufondaonline.ui.screen.ValorCard
import org.junit.Rule
import org.junit.Test

class NosotrosTest {

    @get:Rule
    val composableTestRule = createComposeRule()

    @Test
    fun valores_card_se_muestra_descripcion() {
        composableTestRule.setContent {
            ValorCard(
                icon = Icons.Default.RestaurantMenu,
                title = "Tradición",
                description = "Recetas de familia."
            )
        }
        composableTestRule.onNodeWithText("Recetas de familia.").assertIsDisplayed()
        composableTestRule.onNodeWithTag("TEXTO_DESC_VALORES").assertIsDisplayed()

    }

    @Test
    fun nuestra_mision_desc_se_muestra() {
        composableTestRule.setContent {
            NuestraMisionCard()
        }
        composableTestRule.onNodeWithTag("TEXTO_DESC_MISION").assertIsDisplayed()
    }
}



