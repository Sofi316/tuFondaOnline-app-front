package com.example.tufondaonline.model

data class Producto(
    val id: Long=0,
    val nombre: String="",
    val descripcion: String="",
    val precio: Int=0,
    val stock: Int=0,
    val img: String="",
    val enOferta: Boolean=false,
    val precioOferta: Int?=null,
    val categoria: Categoria? = null
)

data class Categoria(
    val id: Long,
    val nombre: String
)


