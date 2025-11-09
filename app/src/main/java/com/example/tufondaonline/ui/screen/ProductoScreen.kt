package com.example.tufondaonline.ui.screen

import android.R
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.tufondaonline.data.DataSource
import com.example.tufondaonline.model.Producto

@Composable
fun MostrarProducto(producto: Producto){
    var expandido by remember { mutableStateOf(false) }
    var contexto= LocalContext.current
    Card(
        modifier=Modifier.padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ){
        Column(
            modifier=Modifier.animateContentSize(
                animationSpec=spring(
                    dampingRatio= Spring.DampingRatioMediumBouncy,
                    stiffness=Spring.StiffnessLow
                )
            )
        ){
            Image(
                painterResource(producto.image),
                contentDescription = "Foto comida",
                Modifier
                    .fillMaxWidth()
                    .height(if (expandido) 300.dp else 194.dp)
                    .clickable{expandido=!expandido}
            )
            Row(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text=producto.name,
                    style=MaterialTheme.typography.titleMedium
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if(producto.enOferta && producto.precioOferta!=null){
                        Text("$${producto.precio}",
                            style=MaterialTheme.typography.bodyMedium,
                            color=Color.Gray,
                            textDecoration = TextDecoration.LineThrough,
                            modifier=Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = "$${producto.precioOferta}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    }else{
                        Text(
                            text = "$${producto.precio}",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }


                }
            }
            Button(
                onClick = {
                    Toast.makeText(contexto, "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Agregar al carrito")
            }
            if (expandido){
                Text(
                    text=producto.descripcion,
                    style=MaterialTheme.typography.bodyMedium,
                    modifier=Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun ProductoScreen(){
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(DataSource.productos){ producto ->
                MostrarProducto(producto)
            }
        }
    }
}