package com.example.tufondaonline.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tufondaonline.model.Contacto
import com.example.tufondaonline.model.ContactoErrores
import com.example.tufondaonline.model.UsuarioErrores
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ContactoViewModel:  ViewModel() {
    private val _contacto = MutableStateFlow(Contacto())
    val contacto: StateFlow<Contacto> = _contacto

    fun onChangeAsunto(asunto: String){
        _contacto.update {
            it.copy(
                asunto = asunto,
                erroresContacto = it.erroresContacto.copy(asunto = null )
            )
        }
    }
    fun onChangeMensaje(mensaje: String){
        _contacto.update {
            it.copy(
                mensaje = mensaje,
                erroresContacto = it.erroresContacto.copy(mensaje= null)
            )
        }
    }

    fun validarContacto(): Boolean{
        val u = _contacto.value
        val errorContacto = ContactoErrores(
            asunto = if(u.asunto.isBlank()) "Debe llenar este casillero" else null,
            mensaje = when {
                u.mensaje.isBlank() -> "El mensaje no puede estar vacío"
                u.mensaje.length > 100 -> "El mensaje no puede superar los 100 caracteres"
                else -> null
            }
        )
        _contacto.update {
            it.copy(erroresContacto = errorContacto)
        }
        if (errorContacto.asunto==null &&
            errorContacto.mensaje==null){
            return true
        }else{
            return false
        }
    }
}