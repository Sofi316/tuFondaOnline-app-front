package com.example.tufondaonline.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tufondaonline.R
import com.example.tufondaonline.ui.theme.TuFondaOnlineTheme
import com.example.tufondaonline.ui.theme.darkBlue
import kotlinx.coroutines.delay
import com.example.tufondaonline.data.DataSource
import com.example.tufondaonline.model.Producto
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.ui.text.font.FontStyle
import com.example.tufondaonline.ui.components.SectionTitle
@Composable

fun TestimonialCard(quote: String, author: String, modifier: Modifier = Modifier) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row {
                repeat(5) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Estrella de calificación",
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "\"$quote\"",
                style = MaterialTheme.typography.bodyMedium,
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "- $author",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
    }
}
@Composable
fun CardBienvenida() {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = "Bienvenido a tu fonda Online",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                color= MaterialTheme.colorScheme.darkBlue
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Celebremos nuestras raíces con sabores típicos chilenos. Nuestra misión es llevar lo mejor de la gastronomía chilena a tu hogar.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun CardConImagenDeFondo() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ) {
            Image(
                painter = painterResource(id = R.drawable.fondacard),
                contentDescription = "Fonda",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
            )
            Text(
                text = "Recuerda usar\nFELICES18 para un\n18% de descuento",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Start
            )

        }
    }
}



@Composable
fun ProductCard(producto: Producto, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.width(150.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = producto.image),
            contentDescription = producto.name,
            modifier = Modifier
                .size(140.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = producto.name,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            textAlign = TextAlign.Center
        )

        Text(
            text = "$${producto.precio}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

@Composable
fun CarruselDeImagenes() {
    val productos = DataSource.productos
    if (productos.isEmpty()) return

    val lazyListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        while(true) {
            delay(3000)
            lazyListState.animateScrollToItem(index = lazyListState.firstVisibleItemIndex + 1)
        }
    }

    LazyRow(
        state = lazyListState,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(count = Int.MAX_VALUE) { index ->

            val producto = productos[index % productos.size]

            ProductCard(producto = producto)
        }
    }
}

@Composable
fun HomeScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            CardConImagenDeFondo()}
        item {
            Spacer(modifier = Modifier.height(16.dp))
            CardBienvenida()
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            SectionTitle(title = "Productos Destacados")
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            CarruselDeImagenes()
        }

        item {
            Spacer(modifier = Modifier.height(24.dp)) //
            SectionTitle(title = "Lo que dicen nuestros clientes")
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))

            TestimonialCard(
                quote = "¡Las mejores empanadas que he probado en años! Llegaron calientitas y a tiempo.",
                author = "Fernanda R."
            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))

            TestimonialCard(
                quote = "El terremoto estaba perfecto, ni muy dulce ni muy fuerte. 100% recomendado para el 18.",
                author = "Matías L."
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    TuFondaOnlineTheme {
        HomeScreen()
    }
}

