package com.example.tufondaonline.model

data class Usuario(
    val rut: String="",
    val nombre: String="",
    val apellido: String="",
    val correo: String="",
    val direccion: String="",
    val password: String="",
    val aceptarTerminos: Boolean = false,
    val mensaje: String="",
    val errores: UsuarioErrores = UsuarioErrores()
)

data class UsuarioErrores(
    val rut: String? = null,
    val nombre: String? = null,
    val apellido: String? = null,
    val correo: String? = null,
    val direccion: String? = null,
    val password: String? = null,
    val mensaje: String?=null,
    val acepterTerminos: String?=null
)