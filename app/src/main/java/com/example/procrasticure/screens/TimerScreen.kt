package com.example.procrasticure.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.commandiron.wheel_picker_compose.WheelTimePicker
import com.commandiron.wheel_picker_compose.core.TimeFormat
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import com.example.procrasticure.R
import com.example.procrasticure.timer.CountDownView
import com.example.procrasticure.widgets.TopMenu
import java.time.LocalTime


@Composable
fun Timer(navController: NavController) {

    Column {
        TopMenu(
            arrowBackClicked = { navController.popBackStack() },
            heading = "Timer"
        )
        getTimerInfo()
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

            Text(
                text = "Type in the time in minutes!", fontSize = 25.sp, fontFamily = FontFamily(
                    Font(R.font.poppins_semibold)
                )
            )

            WheelTimePicker(
                startTime = LocalTime.of(0,0),
                rowCount = 3,
                textStyle = MaterialTheme.typography.subtitle1,
                textColor = Color.Black,
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    enabled = true,
                    shape = RoundedCornerShape(0.dp),
                    color = Color(0xFFf1faee).copy(alpha = 0.2f),
                    border = BorderStroke(2.dp, Color(0xFFf1faee))
                )
            ){snappedTime -> input = snappedTime.toString() }

            /*OutlinedTextField(
                value = input,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    input = it
                },
            )*/

            Button(onClick = {
                if (input != "") {
                    checked = true
                }
            }) {
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
    val hours = input.dropLast(3)
    val minutes = input.substring(input.length - 2)
    return minutes.toLong()*60000 + hours.toLong()*3600000
}



