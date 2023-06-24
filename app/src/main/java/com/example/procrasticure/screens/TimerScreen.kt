package com.example.procrasticure.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.commandiron.wheel_picker_compose.WheelTimePicker
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import com.example.procrasticure.R
import com.example.procrasticure.timer.CountDownView
import com.example.procrasticure.viewModels.BigViewModel
import com.example.procrasticure.viewModels.UserViewModel
import com.example.procrasticure.widgets.TopMenu
import java.time.LocalTime


@Composable
fun Timer(navController: NavController, userViewModel: UserViewModel, sessionViewModel: BigViewModel) {

    Column {
        TopMenu(
            arrowBackClicked = { navController.popBackStack() },
            heading = "Timer"
        )
        getTimerInfo(userViewModel = userViewModel, sessionViewModel)
    }
}

@Composable
fun getTimerInfo(userViewModel: UserViewModel, sessionViewModel: BigViewModel) {

    var checked by remember {
        mutableStateOf(false)
    }
    var input by remember {
        mutableStateOf("")
    }

    var enable by remember {
        mutableStateOf(true)
    }

    Card(modifier = Modifier.padding(10.dp).fillMaxHeight()) {
        Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {


            AnimatedVisibility(visible = enable) {

                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    
                    Text(text = "Type in the time in minutes!", fontSize = 25.sp, fontWeight = FontWeight.Medium)
                    
                    Spacer(modifier = Modifier.size(10.dp))

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

                    Spacer(modifier = Modifier.size(20.dp))

                    Button(
                        onClick = {
                            if (input != "00:00") {
                                checked = true
                                enable = false
                            }
                        },
                        modifier =
                        Modifier
                            .height(50.dp)
                            .width(200.dp),

                        shape = RoundedCornerShape(25.dp),

                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF673AB7),
                            contentColor = colorResource(id = R.color.white),
                        ),

                        ) {

                        Text("Start timer!",
                            fontSize = 20.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }


            AnimatedVisibility(visible = checked) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) {
                    Column {
                        println(input)
                        println(convertTime(input))
                        val newinput = convertTime(input)
                        CountDownView(newinput, userViewModel = userViewModel, sessionViewModel)
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



