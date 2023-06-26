package com.example.procrasticure.screens

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.procrasticure.viewModels.GoalsViewModel
import com.example.procrasticure.widgets.DatePickerWidget
import com.example.procrasticure.widgets.TimePickerWidget
import com.example.procrasticure.widgets.TopMenu
import kotlinx.coroutines.launch

@Composable
fun UpdateGoalScreen(
    goalID: String,
    name: String?,
    description: String?,
    date: String?,
    time: String?,
    navController: NavController,
    goalsViewModel: GoalsViewModel
) {

    val context = LocalContext.current


    var name by remember { mutableStateOf(name.toString()) }
    var description by remember { mutableStateOf(description.toString()) }
    var date by remember { mutableStateOf(date.toString()) }
    var time by remember { mutableStateOf(time.toString()) }

    val coroutineScope = rememberCoroutineScope()

    Card(
        Modifier
            .background(color = Color.White)
            .fillMaxSize()
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TopMenu(
                arrowBackClicked = {
                    navController.popBackStack()
                },
                heading = "Update A Goal"
            )
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Row {
                OutlinedTextField(label = { Text("Name") },
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.width(320.dp),
                    placeholder = {
                        Text(text = "Change the name of your goal", fontSize = 14.sp)
                    })
            }
            Row {
                OutlinedTextField(label = { Text("Description (Optional)") },
                    value = description,
                    onValueChange = { description = it },
                    modifier = Modifier
                        .width(320.dp)
                        .defaultMinSize(minHeight = 120.dp),
                    placeholder = {
                        Text(text = "Change the description for your goal", fontSize = 14.sp)
                    })
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Row {
                date = DatePickerWidget(dateText = date)
            }
            Spacer(modifier = Modifier.padding(4.dp))

            Row {
                time = TimePickerWidget(timeText = time)
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Button(onClick = {
                coroutineScope.launch {
                    goalsViewModel.modifyGoal(
                        goalID,
                        name,
                        description,
                        date,
                        time,
                        context
                    )
                }
                navController.popBackStack(Screen.GoalsScreen.route, false)
                navController.navigate(Screen.ManageGoalsScreen.route)
            }) {
                Text(text = "Submit", fontSize = 18.sp)
            }

        }
    }
}




