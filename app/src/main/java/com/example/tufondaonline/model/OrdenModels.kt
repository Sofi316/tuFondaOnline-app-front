package com.example.tufondaonline.model

import com.google.gson.annotations.SerializedName

data class OrdenRequest(
    val usuario: UsuarioId,
    val total: Int,
    val estado: String = "PENDIENTE"
)


data class OrdenResponse(
    val id: Long,
    val fechaOrden: String?,
    val estado: String,
    val total: Int,
    val usuario: Usuarios
)


data class DetalleEnvio(
    val orden: OrdenId,
    val producto: ProductoId,
    val cantidad: Int,
    @SerializedName("precio")
    val precio: Int
)

data class UsuarioId(val id: Long)
data class OrdenId(val id: Long)
data class ProductoId(val id: Long)

data class CarritoItem(
    val producto: Producto,
    var cantidad: Int
) {
    val total: Int get() = if (producto.enOferta && producto.precioOferta != null)
        producto.precioOferta * cantidad
    else
        producto.precio * cantidad
}