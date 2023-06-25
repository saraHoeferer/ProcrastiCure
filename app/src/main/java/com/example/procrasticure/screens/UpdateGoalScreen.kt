package com.example.procrasticure.screens

import android.util.Log
import android.widget.Toast
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
import com.example.procrasticure.widgets.DatePickerWidget
import com.example.procrasticure.widgets.TimePickerWidget
import com.example.procrasticure.widgets.TopMenu
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun UpdateGoalScreen(goalID: String, name: String?, description: String?, date: String?, time: String?,navController: NavController){

    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()


    var name by remember { mutableStateOf(name.toString()) }
    var description by remember{ mutableStateOf(description.toString()) }
    var date by remember{ mutableStateOf(date.toString()) }
    var time by remember{ mutableStateOf(time.toString()) }

    Log.d("NAME", name)
    Log.d("DESCRIPTION", description)
    Log.d("DATE", date)
    Log.d("TIME", time)

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
            Row() {
                name.let {
                    OutlinedTextField(label = { Text("Name") }, value = it, onValueChange = {name = it},
                        modifier = Modifier.width(320.dp), placeholder = {
                            Text(text = "Change the name of your goal", fontSize = 14.sp)
                        })
                }
            }
            Row() {
                description.let {
                    OutlinedTextField(label = { Text("Description (Optional)") }, value = it, onValueChange = {description = it},
                        modifier = Modifier
                            .width(320.dp)
                            .defaultMinSize(minHeight = 120.dp), placeholder = {
                            Text(text = "Change the description for your goal", fontSize = 14.sp)
                        })
                }
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Row() {
                date = date.let { DatePickerWidget(dateText = it) }.toString()
            }
            Spacer(modifier = Modifier.padding(4.dp))

            Row {
                time = time.let { TimePickerWidget(timeText = it) }.toString()
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Button(onClick = {

                db.collection("Goals")
                    .document(goalID)
                    .update("name", name, "description", description, "time", time, "name", name)
                    .addOnSuccessListener {
                        name = ""
                        description = ""
                        date = ""
                        time = ""
                        Toast.makeText(context, "Goal Updated", Toast.LENGTH_SHORT).show()

                    }
                    .addOnFailureListener {
                        println("Failure adding goal")
                    }
            }) {
                Text(text = "Submit", fontSize = 18.sp)
            }

        }
    }
}

@Composable
fun UpdateSubGoalScreen(goalID: String, name: String?, description: String?, date: String?, time: String?,navController: NavController, subGoalID: String?){

    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()


    var name by remember { mutableStateOf(name.toString()) }
    var description by remember{ mutableStateOf(description.toString()) }
    var date by remember{ mutableStateOf(date.toString()) }
    var time by remember{ mutableStateOf(time.toString()) }

    Log.d("NAME", name)
    Log.d("DESCRIPTION", description)
    Log.d("DATE", date)
    Log.d("TIME", time)

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
            Row() {
                name.let {
                    OutlinedTextField(label = { Text("Name") }, value = it, onValueChange = {name = it},
                        modifier = Modifier.width(320.dp), placeholder = {
                            Text(text = "Change the name of your goal", fontSize = 14.sp)
                        })
                }
            }
            Row() {
                description.let {
                    OutlinedTextField(label = { Text("Description (Optional)") }, value = it, onValueChange = {description = it},
                        modifier = Modifier
                            .width(320.dp)
                            .defaultMinSize(minHeight = 120.dp), placeholder = {
                            Text(text = "Change the description for your goal", fontSize = 14.sp)
                        })
                }
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Row() {
                date = date.let { DatePickerWidget(dateText = it) }.toString()
            }
            Spacer(modifier = Modifier.padding(4.dp))

            Row {
                time = time.let { TimePickerWidget(timeText = it) }.toString()
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Button(onClick = {

                db.collection("Goals")
                    .document(goalID)
                    .collection("SubGoals")
                    .document(subGoalID.toString())
                    .update("name", name, "description", description, "time", time, "name", name)
                    .addOnSuccessListener {
                        name = ""
                        description = ""
                        date = ""
                        time = ""
                        Toast.makeText(context, "Goal Updated", Toast.LENGTH_SHORT).show()

                    }
                    .addOnFailureListener {
                        println("Failure adding goal")
                    }
            }) {
                Text(text = "Submit", fontSize = 18.sp)
            }

        }
    }
}





