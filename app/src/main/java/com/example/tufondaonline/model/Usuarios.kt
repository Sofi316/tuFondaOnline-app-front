package com.example.tufondaonline.model

data class Usuarios(
    val rut: String="",
    val nombre: String="",
    val apellido: String="",
    val correo: String="",
    val fechaNac: String="",
    val direccion: String="",
    val region: String="",
    val comuna: String="",
    val password: String="",
    val aceptarTerminos: Boolean = false,
    val errores: UsuarioErrores = UsuarioErrores()

)
data class UsuarioErrores(
    val rut: String? = null,
    val nombre: String? = null,
    val apellido: String? = null,
    val correo: String? = null,
    val fechaNac: String? = null,
    val direccion: String? = null,
    val region: String? = null,
    val comuna: String? = null,
    val password: String? = null,
    val acepterTerminos: String?=null
)