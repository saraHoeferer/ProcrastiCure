package com.example.procrasticure.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.procrasticure.widgets.TopMenu
import com.example.procrasticure.R
import com.example.procrasticure.data.Animals

@Composable
fun AnimalShopScreen(navController: NavController){
    Column() {
        TopMenu(arrowBackClicked = { navController.popBackStack() },
            heading = "My Shop")
        AnimalShop(listOf(
            Animals(R.drawable.chicken, "100 Points"),
            Animals(R.drawable.pig, "150 Points"),
            Animals(R.drawable.sheep, "200 Points"),
            Animals(R.drawable.cow, "250 Points")
        ))
    }
}


@Composable
fun AnimalShop(animals: List<Animals>){

    LazyColumn(
        modifier = Modifier
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,){
        items(animals) { animal ->
            AnimalRow(animal = animal.url, text = animal.price)
            Spacer(modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun AnimalRow(animal: Int, text: String){

    Row(modifier = Modifier
        .background(color = Color(0xFFE7E0F5))
        .padding(30.dp)
        .fillMaxWidth()) {
        Image(painter = painterResource(id = animal), contentDescription = "...", modifier = Modifier.size(150.dp,150.dp))

        Spacer(modifier = Modifier.size(60.dp))

        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Text(text = text, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.size(20.dp))

            Button(
                onClick = { /*TODO*/ },
                border = BorderStroke(4.dp,Color(0xFF673AB7)), shape = CutCornerShape(10)) {
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "", Modifier.size(20.dp))
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = "BUY")
            }
        }
    }
}

