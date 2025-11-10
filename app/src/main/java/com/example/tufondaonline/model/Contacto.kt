package com.example.tufondaonline.model

data class Contacto(
    val asunto: String = "",
    val mensaje: String = "",
    val erroresContacto: ContactoErrores = ContactoErrores()
)

data class ContactoErrores(
    val asunto: String? = null,
    val mensaje: String? = null
)
