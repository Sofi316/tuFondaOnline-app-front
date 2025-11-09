package com.example.tufondaonline.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tufondaonline.model.UsuarioErrores
import com.example.tufondaonline.model.Usuarios
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import android.net.Uri
import kotlinx.coroutines.flow.asStateFlow

class UsuarioViewModel: ViewModel() {
    private val _usuario = MutableStateFlow(Usuarios())
    val usuario: StateFlow<Usuarios> =_usuario
    private val _imagenPerfilUri = MutableStateFlow<Uri?>(null)
    val imagenPerfilUri: StateFlow<Uri?> = _imagenPerfilUri.asStateFlow()

    fun onCambiarImagenUri(uri: Uri?) {
        _imagenPerfilUri.value = uri
    }
    fun onChangeRut(rut: String){
        _usuario.update {
            it.copy(
                rut = rut,
                errores = it.errores.copy(rut = null )
            )
        }
    }
    fun onChangeNombre(nombre: String){
        _usuario.update {
            it.copy(
                nombre = nombre,
                errores = it.errores.copy(nombre = null )
            )
        }
    }

    fun onChangeApellido(apellido: String){
        _usuario.update {
            it.copy(
                apellido = apellido,
                errores = it.errores.copy(apellido = null )
            )
        }
    }

    fun onChangeCorreo(correo: String){
        _usuario.update {
            it.copy(
                correo = correo,
                errores = it.errores.copy(correo = null )
            )
        }
    }

    fun onChangeDireccion(direccion: String){
        _usuario.update {
            it.copy(
                direccion = direccion,
                errores = it.errores.copy(direccion = null )
            )
        }
    }

    fun onChangePassword(pass: String){
        _usuario.update {
            it.copy(
                password = pass,
                errores = it.errores.copy(password = null )
            )
        }
    }

    fun onChangeAceptarTerminos(valor: Boolean){
        _usuario.update { it.copy(aceptarTerminos = valor) }
    }

    fun validarRegistro(): Boolean{
        val u = _usuario.value
        val errores = UsuarioErrores(
            rut = if (u.rut.isBlank()) "El rut no puede estar vacio" else null,
            nombre = if (u.nombre.isBlank()) "El nombre no puede estar vacio" else null,
            apellido = if (u.apellido.isBlank()) "El apellido no puede estar vacio" else null,
            correo = if (u.correo.isBlank() || !u.correo.contains("@")) "Ingrese un formato valido" else null,
            direccion = if (u.direccion.isBlank()) "La direccion no puede estar vacia" else null,
            password = if (u.password.isBlank()) "La contraseña no puede estar vacía" else null,
            acepterTerminos = if(u.aceptarTerminos==false) "Debe aceptar los términos de la empresa" else null
        )
        _usuario.update {
            it.copy(errores = errores)
        }
        if (errores.rut==null &&
            errores.nombre==null &&
            errores.apellido==null &&
            errores.correo==null &&
            errores.direccion== null &&
            errores.password==null &&
            errores.acepterTerminos==null){
            return true
        }else{
            return false
        }
    }
    fun validarLogin(): Boolean{
        val u = _usuario.value
        val errores = UsuarioErrores(
            correo = if (u.correo.isBlank() || !u.correo.contains("@")) "Ingrese un formato valido" else null,
            password = if (u.password.isBlank()) "La contraseña no puede estar vacía" else null,
        )
        _usuario.update {
            it.copy(errores = errores)
        }
        if (errores.correo==null && errores.password==null){
            return true
        }else{
            return false
        }
    }
    fun cargarUsuarioCompleto(rut: String, nombre: String, apellido: String, correo: String, direccion: String, password: String) {
        _usuario.update {
            it.copy(
                rut = rut,
                nombre = nombre,
                apellido = apellido,
                correo = correo,
                direccion = direccion,
                password = password, // Mantenemos la pass por si se necesita en otro lugar.
                errores = UsuarioErrores() // Limpiamos cualquier error previo.
            )
        }
    }
}

