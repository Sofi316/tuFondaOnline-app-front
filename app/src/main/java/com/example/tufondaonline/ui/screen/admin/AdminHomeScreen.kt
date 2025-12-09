package com.example.tufondaonline.ui.screen.admin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tufondaonline.model.OrdenResponse
import com.example.tufondaonline.viewmodel.AdminViewModel

@Composable
fun AdminHomeScreen(viewModel: AdminViewModel,navController: NavController) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Spacer(Modifier.height(20.dp))
            Text(
                text = "Panel de Administración",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AdminCardButton(
                    text = "Productos",
                    icon = Icons.Default.ShoppingCart,
                    onClick = { navController.navigate("AdminProductos") },
                    modifier = Modifier.weight(1f)
                )
                AdminCardButton(
                    text = "Usuarios",
                    icon = Icons.Default.Person,
                    onClick = { navController.navigate("AdminUsuarios") },
                    modifier = Modifier.weight(1f)
                )
                AdminCardButton(
                    text = "Ordenes",
                    icon = Icons.Default.ShoppingCart,
                    onClick = { navController.navigate("AdminOrdenes") },
                    modifier = Modifier.weight(1f)
                )
            }
        }

    }
}

@Composable
fun AdminCardButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(100.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text, fontWeight = FontWeight.Bold)
        }
    }
}

