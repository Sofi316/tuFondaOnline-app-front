package com.example.tufondaonline.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tufondaonline.model.UsuarioErrores
import com.example.tufondaonline.model.Usuarios
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.tufondaonline.model.LoginRequest
import com.example.tufondaonline.repository.UsuarioRepository
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel: ViewModel() {
    private val _usuario = MutableStateFlow(Usuarios())
    val usuario: StateFlow<Usuarios> =_usuario
    private val _imagenPerfilUri = MutableStateFlow<Uri?>(null)
    val imagenPerfilUri: StateFlow<Uri?> = _imagenPerfilUri.asStateFlow()

    private val repository = UsuarioRepository()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _loginExitoso = MutableStateFlow(false)
    val loginExitoso = _loginExitoso.asStateFlow()
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

    fun onChangeEmail(email: String){
        _usuario.update {
            it.copy(
                email= email,
                errores = it.errores.copy(email= null )
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

    fun onChangeComuna(idComuna: Long) {}

    fun onChangeRegion(idRegion: Long) {}

    fun validarRegistro(): Boolean{
        val u = _usuario.value
        val errores = UsuarioErrores(
            rut = if (u.rut.isBlank()) "El rut no puede estar vacio" else null,
            nombre = if (u.nombre.isBlank()) "El nombre no puede estar vacio" else null,
            apellido = if (u.apellido.isBlank()) "El apellido no puede estar vacio" else null,
            email= if (u.email.isBlank() || !u.email.contains("@")) "Ingrese un formato valido" else null,
            direccion = if (u.direccion.isBlank()) "La direccion no puede estar vacia" else null,
            password = if (u.password.isBlank()) "La contraseña no puede estar vacía" else null,
            aceptarTerminos = if(u.aceptarTerminos==false) "Debe aceptar los términos de la empresa" else null
        )
        _usuario.update {
            it.copy(errores = errores)
        }
        if (errores.rut==null &&
            errores.nombre==null &&
            errores.apellido==null &&
            errores.email==null &&
            errores.direccion== null &&
            errores.password==null &&
            errores.aceptarTerminos==null){
            return true
        }else{
            return false
        }
    }
    fun validarLogin(): Boolean{
        val u = _usuario.value
        val errores = UsuarioErrores(
            email= if (u.email.isBlank() || !u.email.contains("@")) "Ingrese un formato valido" else null,
            password = if (u.password.isBlank()) "La contraseña no puede estar vacía" else null,
        )
        _usuario.update {
            it.copy(errores = errores)
        }
        if (errores.email==null && errores.password==null){
            return true
        }else{
            return false
        }
    }
    fun intentarLogin() {
        if (validarLogin()) {
            viewModelScope.launch {
                _isLoading.value = true
                val u = _usuario.value
                try {
                    val response = repository.hacerLogin(LoginRequest(u.email, u.password))

                    if (response.isSuccessful && response.body() != null) {
                        val usuarioRecibido = response.body()!!

                        _usuario.update {
                            usuarioRecibido.copy(
                                password = "",
                                errores = UsuarioErrores()
                            )
                        }

                        _loginExitoso.value = true
                    } else {
                        val error = UsuarioErrores(password = "Credenciales incorrectas")
                        _usuario.update { it.copy(errores = error) }
                    }
                } catch (e: Exception) {
                    val error = UsuarioErrores(email = "Error de conexión")
                    _usuario.update { it.copy(errores = error) }
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }

    fun intentarRegistro() {
        if (validarRegistro()) {
            viewModelScope.launch {
                _isLoading.value = true
                try {
                    val u = _usuario.value
                    val nombreCompleto = "${u.nombre.trim()} ${u.apellido.trim()}"
                    val usuarioParaEnviar = u.copy(nombre = nombreCompleto)

                    val response = repository.hacerRegistro(usuarioParaEnviar)

                    if (response.isSuccessful) {

                        intentarLogin()
                    } else {
                        val error = UsuarioErrores(email = "Error: El correo ya existe")
                        _usuario.update { it.copy(errores = error) }
                    }
                } catch (e: Exception) {
                    val error = UsuarioErrores(email = "Sin conexión")
                    _usuario.update { it.copy(errores = error) }
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }

    fun cerrarSesion() {
        _loginExitoso.value = false
        _usuario.value = Usuarios()
    }
    fun cargarUsuarioCompleto(rut: String, nombre: String, apellido: String, correo: String, direccion: String, password: String) {
        _usuario.update {
            it.copy(
                rut = rut,
                nombre = nombre,
                apellido = apellido,
                email= correo,
                direccion = direccion,
                password = password,
                errores = UsuarioErrores()
            )
        }
    }
}

