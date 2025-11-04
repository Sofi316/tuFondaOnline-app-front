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
    val nombre: String? = null,
    val correo: String? = null,
    val password: String? = null
)