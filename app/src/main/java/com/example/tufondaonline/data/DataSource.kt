package com.example.tufondaonline.data

import com.example.tufondaonline.R
import com.example.tufondaonline.model.Producto

object DataSource {
    val productos = listOf(
        Producto(
            name = "Agua",
            descripcion = "Agua mineral sin gas de los manantiales más cristalinos de Chile, botella de 500cc.",
            precio = 1800,
            categoria = "Bebestible",
            image = R.drawable.agua
        ),
        Producto(
            name = "Terremoto",
            descripcion = "Tradicional trago chileno con vino pipeño blanco, helado de piña y granadina.",
            precio = 3500,
            categoria = "Bebestible",
            image = R.drawable.terremoto
        ),
        Producto(
            name = "Coca-cola",
            descripcion = "Clásica bebida Coca-Cola en formato lata o botella.",
            precio = 2500,
            categoria = "Bebestible",
            image = R.drawable.cocacola
        ),
        Producto(
            name = "Anticucho",
            descripcion = "Trozos jugosos de carne de vacuno, longaniza, cebolla y pimentón asados a la parrilla en brocheta.",
            precio = 10000,
            categoria = "Plato con carne",
            image = R.drawable.anticucho
        ),
        Producto(
            name = "Empanada de pino",
            descripcion = "Masa rellena de carne picada, cebolla, aceitunas, pasas y huevo duro.",
            precio = 5000,
            categoria = "Plato con carne",
            image = R.drawable.empanada
        ),
        Producto(
            name = "Choripán",
            descripcion = "Delicioso chorizo de Chillán asado a la parrilla dentro de un crujiente pan marraqueta.",
            precio = 3000,
            categoria = "Plato con carne",
            image = R.drawable.choripan
    ),
        Producto(
            name = "Choripán vegano",
            descripcion = "Versión vegana del clásico choripán, con chorizo a base de plantas en pan marraqueta.",
            precio = 3000,
            categoria = "Plato vegetariano",
            image = R.drawable.choripanveg
        ),
        Producto(
            name = "Anticucho de verduras",
            descripcion = "Brocheta de verduras de temporada (pimentón, cebolla, zapallo italiano, champiñón) asadas a la parrilla.",
            precio = 8000,
            categoria = "Plato vegetariano",
            image = R.drawable.anticuchoverdura
        ),
        Producto(
            name = "Pastel de choclo vegano",
            descripcion = "Versión vegana del pastel de choclo, con pino a base de soya texturizada y verduras, cubierto de pasta de choclo.",
            precio = 3000,
            categoria = "Plato vegetariano",
            image = R.drawable.pastelchocloveg
        ),
    )
}
