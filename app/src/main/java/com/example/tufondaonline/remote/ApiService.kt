package com.example.tufondaonline.remote

import com.example.tufondaonline.model.Categoria
import com.example.tufondaonline.model.Comuna
import com.example.tufondaonline.model.DetalleEnvio
import com.example.tufondaonline.model.LoginRequest
import com.example.tufondaonline.model.OrdenRequest
import com.example.tufondaonline.model.OrdenResponse
import com.example.tufondaonline.model.Producto
import com.example.tufondaonline.model.Region
import com.example.tufondaonline.model.Usuarios
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<Usuarios>

    @POST("auth/register")
    suspend fun registro(@Body request: Usuarios): Response<Usuarios>

    @GET("api/regiones")
    suspend fun obtenerRegiones(): Response<List<Region>>

    @GET("api/comunas/{idRegion}")
    suspend fun obtenerComunasPorRegion(
        @Path("idRegion") idRegion: Long
    ): Response<List<Comuna>>

    @POST("api/ordenes")
    suspend fun crearOrden(@Body orden: OrdenRequest): Response<OrdenResponse>

    @POST("api/detalle_orden")
    suspend fun crearDetalle(@Body detalle: DetalleEnvio): Response<Void>

    @GET("api/productos")
    suspend fun obtenerProductos(): Response<List<Producto>>
    @GET("api/ordenes")
    suspend fun obtenerTodasLasOrdenes(): Response<List<OrdenResponse>>

    @POST("api/productos")
    suspend fun crearProducto(@Body producto: Producto): Response<Producto>

    @PUT("api/productos/{id}")
    suspend fun actualizarProducto(
        @Path("id") id: Long,
        @Body producto: Producto
    ): Response<Producto>

    @DELETE("api/productos/{id}")
    suspend fun eliminarProducto(
        @Path("id") id: Long
    ): Response<Void>

    @GET("api/productos/{id}")
    suspend fun obtenerProductoPorId(
        @Path("id") id: Long
    ): Response<Producto>

    @GET("api/usuarios")
    suspend fun obtenerUsuarios(): Response<List<Usuarios>>

    @POST("api/usuarios")
    suspend fun crearUsuario(@Body usuario: Usuarios): Response<Usuarios>

    @PUT("api/usuarios/{id}")
    suspend fun actualizarUsuario(
        @Path("id") id:Long,
        @Body usuario: Usuarios
    ): Response<Usuarios>
    @DELETE("api/usuarios/{id}")
    suspend fun eliminarUsuario(
        @Path("id") id: Long
    ): Response<Void>
    @GET("api/usuarios/{id}")
    suspend fun obtenerUsuarioPorId(
        @Path("id") id: Long
    ): Response<Usuarios>
    @GET("api/categorias")
    suspend fun obtenerCategorias(): Response<List<Categoria>>
    @GET("api/categorias/{id}")
    suspend fun obtenerCategoriaPorId(
        @Path("id") id: Long
    ): Response<Categoria>


}
