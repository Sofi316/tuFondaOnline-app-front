package com.example.tufondaonline

import com.example.tufondaonline.model.Categoria
import com.example.tufondaonline.model.Producto
import com.example.tufondaonline.remote.ApiService
import com.example.tufondaonline.repository.AdminProductosRepositoryT
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response
import junit.framework.TestCase.assertEquals
class AdminRepositoryTest {
    private val mockApiService = mockk<ApiService>()
    private val repository = AdminProductosRepositoryT(mockApiService)

    @Test
    fun obtener_categorias()= runTest {
        val cat= listOf(
            Categoria(1, "Postres"),
            Categoria(2,"Salados")
        )
        val mockResponse = Response.success(cat)
        coEvery { mockApiService.obtenerCategorias() }returns mockResponse
        val resultado= repository.obtenerCategorias()
        assertEquals(cat,resultado)

    }

    @Test
    fun obtener_productos()= runTest {
        val productos = listOf(
            Producto(1,"Charquicán","Delicioso plato típico chileno",
                5000,10,"Charquican.jpg",false,null,null),
            Producto(2,"Porotos","Porotos con riendas",7000,20,
                "Porotos.jpg",false,null,null)
        )
        val mockResponse = Response.success(productos)
        coEvery { mockApiService.obtenerProductos() }returns mockResponse
        val resultado = repository.obtenerProductos()
        assertEquals(productos,resultado.body())
    }
}