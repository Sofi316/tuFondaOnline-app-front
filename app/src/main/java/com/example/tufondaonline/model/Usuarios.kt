package com.example.tufondaonline.model

import com.google.gson.annotations.SerializedName

data class Usuarios(
    val id: Long = 0,
    val rut: String = "",
    val nombre: String = "",
    val apellido: String = "",
    val email: String = "",
    val rol: String = "",
    val direccion: String = "",
    val comuna: Comuna? = null,
    @SerializedName("password")
    val password: String = "",
    val aceptarTerminos: Boolean = false,
    val errores: UsuarioErrores = UsuarioErrores()
)

data class Comuna(
    val id: Long,
    val nombre: String,
    val region: Region?
)

data class Region(
    val id: Long,
    val nombre: String
)

data class UsuarioErrores(
    val rut: String? = null,
    val nombre: String? = null,
    val apellido: String? = null,
    val email: String? = null,
    val direccion: String? = null,
    val password: String? = null,
    val aceptarTerminos: String? = null
)