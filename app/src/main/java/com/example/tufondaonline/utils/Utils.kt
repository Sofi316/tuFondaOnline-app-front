package com.example.tufondaonline.utils

import android.content.Context
import androidx.compose.runtime.Composable
import com.example.tufondaonline.R

@Composable
fun obtenerImagenLocal(nombreImagen: String, context: Context): Int {
    val nombreLimpio = nombreImagen.lowercase().trim()

    val id = context.resources.getIdentifier(
        nombreLimpio,
        "drawable",
        context.packageName
    )

    return if (id != 0) id else R.drawable.placeholder_imagen
}