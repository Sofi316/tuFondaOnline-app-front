package com.example.tufondaonline.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tufondaonline.R
import kotlinx.coroutines.delay

@Composable
fun CardBienvenida() {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .width(240.dp)
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
                style = MaterialTheme.typography.titleMedium
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
            .height(200.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.fondacard),
                contentDescription = "Fonda",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = "FELICES18 para un 18% de descuento",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
fun ProductosDestacados() {
    Text(
        text = "Productos destacados",
        modifier = Modifier.padding(16.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun CarruselDeImagenes() {
    val images = listOf(
        R.drawable.choripan,
        R.drawable.empanada,
        R.drawable.anticucho,
        R.drawable.terremoto,
    )
    if (images.isEmpty()) return

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
            val imageIndex = index % images.size
            Image(
                painter = painterResource(id = images[imageIndex]),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CardDefaults.shape),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CardConImagenDeFondo()
        Spacer(modifier = Modifier.height(16.dp))
        CardBienvenida()
        Spacer(modifier = Modifier.height(16.dp))
        ProductosDestacados()
        Spacer(modifier = Modifier.height(16.dp))
        CarruselDeImagenes()
    }
}
