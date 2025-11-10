package com.example.tufondaonline.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = Color(0xFF1C1B1F),
    surface = Color(0xFF2C2B2F),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color(0xFFFFFBFE),
    onSurface = Color(0xFFFFFBFE)
)

private val LightColorScheme = lightColorScheme(
    primary = DarkBlue, // Color primario para botones y elementos principales
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = White_Fondo,
    surface = Grey_Card,
    onPrimary = Color.White, // Color del texto sobre el color primario (ej. en botones)
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.Black, // Color del texto sobre el fondo de la pantalla
    onSurface = Color.Black // Color del texto sobre superficies (como TextFields)
)

@Composable
fun TuFondaOnlineTheme(
    darkTheme: Boolean = false, 
    dynamicColor: Boolean = false, 
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

val ColorScheme.darkBlue: Color
    @Composable
    get() = DarkBlue

val ColorScheme.lightBlue: Color
    @Composable
    get() = LightBlue