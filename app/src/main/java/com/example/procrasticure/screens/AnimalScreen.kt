package com.example.procrasticure.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.procrasticure.R
import com.example.procrasticure.widgets.TopMenu

@Composable
fun AnimalScreen(navController: NavController){
    Column() {
        TopMenu(arrowBackClicked = { navController.popBackStack() },
            heading = "My Animals")
        AnimalDetails(navController)
    }
}

@Composable
fun AnimalDetails(navController: NavController){

    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        //.background(color = Color(0xFF046D21))
    ) {
        Box(){

            Image(painter = painterResource(id = R.drawable.grass), contentDescription = "grass",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight())

            Image(painter = painterResource(id = R.drawable.barn), contentDescription = "barn",
                Modifier
                    .size(110.dp, 110.dp)
                    .clickable {
                        navController.navigate(Screen.AnimalShopScreen.route)
                    })

            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.Center)) {

                Spacer(modifier = Modifier.size(150.dp))

                Row(horizontalArrangement = Arrangement.End, modifier = Modifier
                    .fillMaxWidth()) {
                    Image(painter = painterResource(id = R.drawable.cow),
                        contentDescription = "")
                    Spacer(modifier = Modifier.size(50.dp))
                }

                Spacer(modifier = Modifier.size(80.dp))

                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier
                    .fillMaxWidth()) {
                    Image(painter = painterResource(id = R.drawable.sheep),
                        contentDescription = "")
                    Spacer(modifier = Modifier.size(20.dp))
                }

                Row( modifier = Modifier
                    .fillMaxWidth()) {
                    Spacer(modifier = Modifier.size(50.dp))
                    Image(painter = painterResource(id = R.drawable.chicken),
                        contentDescription = "")
                }

                Spacer(modifier = Modifier.size(80.dp))

                Row(modifier = Modifier
                    .fillMaxWidth()) {
                    Spacer(modifier = Modifier.size(200.dp))
                    Image(painter = painterResource(id = R.drawable.pig),
                        contentDescription = "")
                }
            }
        }
    }
}

