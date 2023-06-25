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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.procrasticure.widgets.TopMenu
import com.example.procrasticure.R
import com.example.procrasticure.data.Animals
import com.example.procrasticure.viewModels.AnimalViewModel
import com.example.procrasticure.viewModels.BigViewModel
import kotlinx.coroutines.launch

@Composable
fun AnimalShopScreen(navController: NavController, animalViewModel: AnimalViewModel, sessionViewModel: BigViewModel){
    Column() {
        TopMenu(arrowBackClicked = { navController.popBackStack() },
            heading = "My Shop")
        AnimalShop(listOf(
            Animals("chicken", 100),
            Animals("pig", 150 ),
            Animals("sheep", 200 ),
            Animals("cow", 250)
        ),
        sessionViewModel = sessionViewModel,
        animalViewModel = animalViewModel)
    }
}


@Composable
fun AnimalShop(animals: List<Animals>, animalViewModel: AnimalViewModel, sessionViewModel: BigViewModel){

    LazyColumn(
        modifier = Modifier
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,){
        items(animals) { animal ->
            AnimalRow(
                animal =animal,
            sessionViewModel = sessionViewModel,
            animalViewModel = animalViewModel)
            Spacer(modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun AnimalRow(animal: Animals, animalViewModel: AnimalViewModel, sessionViewModel: BigViewModel){
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Row(modifier = Modifier
        .background(color = Color(0xFFE7E0F5))
        .padding(30.dp)
        .fillMaxWidth()) {
        Image(
            painter = painterResource(
                id =
                    if (animal.url == "chicken") {
                        R.drawable.chicken
                    } else if (animal.url == "pig") {
                        R.drawable.pig
                    } else if (animal.url == "cow"){
                        R.drawable.cow
                    } else {
                        R.drawable.sheep
                    }
            ),
            contentDescription = "...",
            modifier = Modifier.size(150.dp,150.dp)
        )

        Spacer(modifier = Modifier.size(60.dp))

        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Text(text = "${animal.price} Points", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.size(20.dp))

            Button(
                onClick = { coroutineScope.launch{animalViewModel.buyAnimal(sessionViewModel, animal, context)} },
                border = BorderStroke(4.dp,Color(0xFF673AB7)), shape = CutCornerShape(10)) {
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "", Modifier.size(20.dp))
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = "BUY")
            }
        }
    }
}

