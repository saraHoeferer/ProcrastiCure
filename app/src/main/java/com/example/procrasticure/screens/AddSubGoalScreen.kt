package com.example.procrasticure.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.procrasticure.widgets.DatePickerWidget
import com.example.procrasticure.widgets.TimePickerWidget
import com.example.procrasticure.widgets.TopMenu

// add a subgoal

@Composable
fun AddSubGoalScreen(navController: NavController){
    Card(
        Modifier
            .background(color = Color.White)
            .fillMaxSize()
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TopMenu(
                navController = navController,
                arrowBackClicked = { navController.popBackStack() },
                heading = "Add A New Subgoal"
            )
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Row() {
                SimpleTextField(label = "Name", modifier = Modifier)
            }
            Row() {
                SimpleTextField(
                    label = "Description (Optional)",
                    modifier = Modifier.defaultMinSize(minHeight = 120.dp)
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Row() {
                DatePickerWidget(dateText = "Date (Optional)")
            }
            Spacer(modifier = Modifier.padding(4.dp))

            Row {
                TimePickerWidget(timeText = "Time (Optional)")
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Submit", fontSize = 18.sp)
            }

        }
    }
}