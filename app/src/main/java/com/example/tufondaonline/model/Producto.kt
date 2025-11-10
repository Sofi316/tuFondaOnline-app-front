package com.example.tufondaonline.model

data class Producto(
    val name: String="",
    val descripcion: String="",
    val precio: Int=0,
    val precioOferta: Int?=null,
    val categoria: String="",
    val image: Int,
    val enOferta: Boolean=false
)


