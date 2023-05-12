package com.example.procrasticure.Screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.procrasticure.R
import com.example.procrasticure.Timer.CountDownView
import com.example.procrasticure.Widgets.TopMenu

@Composable
fun Timer(navController: NavController) {

    Column {
        TopMenu(navController = navController)
        getTimerInfo()
        //CountDownView()
    }
}


@Composable
fun getTimerInfo() {

    var checked by remember {
        mutableStateOf(false)
    }
    var input by remember {
        mutableStateOf("")
    }

    Card(modifier = Modifier.padding(10.dp)) {
        Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = "Type in the time in minutes!", fontSize = 25.sp, fontFamily = FontFamily(
                Font(R.font.poppins_semibold))
            )

            OutlinedTextField(
                value = input,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    input = it
                },
            )

            Button(onClick = { checked = true }) {
                Text(text = "Start Timer")
            }

            AnimatedVisibility(visible = checked) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Red), horizontalArrangement = Arrangement.Center
                ) {
                    Column {
                        val newinput = convertTime(input)
                        CountDownView(newinput)
                    }
                }
            }
        }
    }
}

fun convertTime(input: String): Long {
    return input.toLong() * 60000
}

/*
@Composable
fun startTimer(time: Int) {

    var minutes by remember {
        mutableStateOf(time)
    }

    Text(text = minutes.toString(), fontSize = 40.sp)

    LaunchedEffect(Unit) {
        if(minutes != 0) {
            minutes -= 1
            //startTimer(time = minutes)
            delay(1000)
        }
    }
}*/


