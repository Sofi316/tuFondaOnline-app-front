package com.example.tufondaonline.data

import com.example.tufondaonline.R
import com.example.tufondaonline.model.Producto

object DataSource {
    val productos = listOf(
        Producto(
            name = "Agua",
            descripcion = "Agua mineral sin gas de los manantiales más cristalinos de Chile, botella de 500cc.",
            precio = 1800,
            precioOferta = 1790,
            categoria = "Bebestible",
            image = R.drawable.agua,
            enOferta = true
        ),
        Producto(
            name = "Terremoto",
            descripcion = "Tradicional trago chileno con vino pipeño blanco, helado de piña y granadina.",
            precio = 3500,
            precioOferta = null,
            categoria = "Bebestible",
            image = R.drawable.terremoto,
            enOferta = false
        ),
        Producto(
            name = "Coca-cola",
            descripcion = "Clásica bebida Coca-Cola en formato lata o botella.",
            precio = 2500,
            precioOferta = null,
            categoria = "Bebestible",
            image = R.drawable.cocacola,
            enOferta = false
        ),
        Producto(
            name = "Anticucho",
            descripcion = "Trozos jugosos de carne de vacuno, longaniza, cebolla y pimentón asados a la parrilla en brocheta.",
            precio = 10000,
            precioOferta = null,
            categoria = "Plato con carne",
            image = R.drawable.anticucho,
            enOferta = false
        ),
        Producto(
            name = "Empanada de pino",
            descripcion = "Masa rellena de carne picada, cebolla, aceitunas, pasas y huevo duro.",
            precio = 5000,
            precioOferta = null,
            categoria = "Plato con carne",
            image = R.drawable.empanada,
            enOferta = false
        ),
        Producto(
            name = "Choripán",
            descripcion = "Delicioso chorizo de Chillán asado a la parrilla dentro de un crujiente pan marraqueta.",
            precio = 3000,
            precioOferta = 2200,
            categoria = "Plato con carne",
            image = R.drawable.choripan,
            enOferta = true
    ),
        Producto(
            name = "Choripán vegano",
            descripcion = "Versión vegana del clásico choripán, con chorizo a base de plantas en pan marraqueta.",
            precio = 3000,
            precioOferta = null,
            categoria = "Plato vegetariano",
            image = R.drawable.choripanveg,
            enOferta = false
        ),
        Producto(
            name = "Anticucho de verduras",
            descripcion = "Brocheta de verduras de temporada (pimentón, cebolla, zapallo italiano, champiñón) asadas a la parrilla.",
            precio = 8000,
            precioOferta = 7999,
            categoria = "Plato vegetariano",
            image = R.drawable.anticuchoverdura,
            enOferta = true
        ),
        Producto(
            name = "Pastel de choclo vegano",
            descripcion = "Versión vegana del pastel de choclo, con pino a base de soya texturizada y verduras, cubierto de pasta de choclo.",
            precio = 3000,
            precioOferta = 2000,
            categoria = "Plato vegetariano",
            image = R.drawable.pastelchocloveg,
            enOferta = true
        ),
    )
}
