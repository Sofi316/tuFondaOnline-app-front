package com.example.tufondaonline.ui.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tufondaonline.R
import com.example.tufondaonline.ui.components.SectionTitle
import com.example.tufondaonline.ui.theme.TuFondaOnlineTheme
import com.example.tufondaonline.ui.theme.darkBlue


@Composable
fun NosotrosScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Spacer(modifier = Modifier.height(16.dp))
            SectionTitle(title = "Nuestra Historia")
        }


        item {
            Spacer(modifier = Modifier.height(8.dp))
            NuestraHistoriaCard()
        }


        item {
            Spacer(modifier = Modifier.height(16.dp))
            SectionTitle(title = "Nuestra Misión")
        }


        item {
            Spacer(modifier = Modifier.height(8.dp))
            NuestraMisionCard()
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            SectionTitle(title = "Nuestros Valores")
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ValorCard(
                    icon = Icons.Default.Favorite,
                    title = "Calidad",
                    description = "Ingredientes frescos y locales.",
                    modifier = Modifier.weight(1f)
                )
                ValorCard(
                    icon = Icons.Default.RestaurantMenu,
                    title = "Tradición",
                    description = "Recetas de familia.",
                    modifier = Modifier.weight(1f)
                )
                ValorCard(
                    icon = Icons.Default.ThumbUp,
                    title = "Servicio",
                    description = "De nuestra cocina a tu mesa.",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            SectionTitle(title = "Conoce al Chef")
        }

        item {
            EquipoCard(
                imageRes = R.drawable.gordonfonda,
                name = "Gordon R.",
                role = "Maestro de la Parrilla y Fundador",
                quote = "Cada plato lleva un trocito de nuestra historia."
            )
        }
    }
}

@Composable
fun NuestraHistoriaCard() {

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {

            Image(
                painter = painterResource(id = R.drawable.fondanosotros),
                contentDescription = "Familia de la fonda",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "De nuestra familia a la tuya",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.darkBlue
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Tu Fonda Online nació en el corazón de nuestra familia, con la simple idea de compartir los" +
                            " sabores que nos han unido por generaciones." +
                            " Cada receta es un tesoro guardado, preparado " +
                            "con el amor y" +
                            " dedicación que se merece cada plato.",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun NuestraMisionCard() {

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp)

    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.fondasom),
                contentDescription = "Familia de la fonda",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Llevar el auténtico sabor chileno a tu mesa, " +
                        "utilizando ingredientes frescos y locales, " +
                        "manteniendo viva la tradición culinaria de " +
                        "nuestro país con cada pedido que entregamos.",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}
@Composable
fun ValorCard(
    icon: ImageVector,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier.height(130.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = icon, contentDescription = title, tint = MaterialTheme.colorScheme.darkBlue)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = description, style = MaterialTheme.typography.labelSmall, textAlign = TextAlign.Center)
        }
    }
}
@Composable
fun EquipoCard(
    imageRes: Int,
    name: String,
    role: String,
    quote: String,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(text = role, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.darkBlue)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "\"$quote\"", style = MaterialTheme.typography.bodySmall, fontStyle = FontStyle.Italic)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NosotrosScreenPreview() {
    TuFondaOnlineTheme {
        NosotrosScreen()
    }
}
