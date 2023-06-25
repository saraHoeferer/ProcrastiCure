package com.example.procrasticure.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.procrasticure.R
import com.example.procrasticure.data.Animals
import com.example.procrasticure.viewModels.AnimalViewModel
import com.example.procrasticure.viewModels.BigViewModel
import com.example.procrasticure.widgets.TopMenu
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AnimalScreen(navController: NavController, animalViewModel: AnimalViewModel, sessionViewModel: BigViewModel){
    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch { animalViewModel.getUserAnimals(sessionViewModel) }
    val animalList = remember {animalViewModel.animals}
    println(animalList)
    Box(modifier =
        Modifier
            .height(1000.dp)
            .paint(
                painter = painterResource(id = R.drawable.grass),
                contentScale = ContentScale.FillHeight
            )
    ) {
        Column() {
            TopMenu(arrowBackClicked = { navController.popBackStack() },
                heading = "My Animals")
            AnimalDetails(navController, animalList)
        }
    }
}

@Composable
fun AnimalDetails(navController: NavController, animalList: ArrayList<Animals>){

    Column {
            Column {
                Image(painter = painterResource(id = R.drawable.barn), contentDescription = "barn",
                    Modifier
                        .size(110.dp, 110.dp)
                        .clickable {
                            navController.navigate(Screen.AnimalShopScreen.route)
                        })
                LazyVerticalGrid(GridCells.Fixed(3)) {
                    items(items = animalList) { animal ->
                        Image(
                            painter = painterResource(
                                id =
                                if (animal.url == "chicken") {
                                    R.drawable.chicken
                                } else if (animal.url == "pig") {
                                    R.drawable.pig
                                } else if (animal.url == "cow") {
                                    R.drawable.cow
                                } else {
                                    R.drawable.sheep
                                }
                            ),
                            contentDescription = "${animal.url}"
                        )
                    }
                }
            }
    }
}

