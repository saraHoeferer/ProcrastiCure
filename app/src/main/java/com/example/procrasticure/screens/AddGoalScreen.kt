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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.procrasticure.data.model.Goal
import com.example.procrasticure.viewModels.GoalsViewModel
import com.example.procrasticure.widgets.DatePickerWidget
import com.example.procrasticure.widgets.TimePickerWidget
import com.example.procrasticure.widgets.TopMenu
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

@Composable
fun AddGoalScreen(navController: NavController) {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    val database = Firebase.database
    val myRef = database.getReference("Goals")

    /*
    val repository = MovieRepository(movieDao = db.movieDao())
    val factory = MoviesViewModelFactory(repository = repository)
    val viewModel: MoviesViewModel = viewModel(factory = factory)
     */

    var name by remember { mutableStateOf("")}
    var description by remember{ mutableStateOf("") }
    var date by remember{ mutableStateOf("") }
    var time by remember{ mutableStateOf("") }

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
                heading = "Add A New Goal"
            )
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Row() {
                OutlinedTextField(label = { Text("Name") }, value = name, onValueChange = {name = it},
                    modifier = Modifier.width(320.dp), placeholder = {
                        Text(text = "Enter the name of your goal", fontSize = 14.sp)
                    })
            }
            Row() {
                OutlinedTextField(label = { Text("Description (Optional)") }, value = description, onValueChange = {description = it},
                    modifier = Modifier
                        .width(320.dp)
                        .defaultMinSize(minHeight = 120.dp), placeholder = {
                        Text(text = "Write a description for your goal", fontSize = 14.sp)
                    })
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Row() {
                date = DatePickerWidget(dateText = date)
            }
            Spacer(modifier = Modifier.padding(4.dp))

            Row {
                time = TimePickerWidget(timeText = time)
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Button(onClick = {
                if(name.isNotEmpty() && date.isNotEmpty()){
                    val goal = Goal(Name=name, Description = description, Date = date, Time = time)
                    db.collection("Goals")
                        .add(goal)
                        .addOnSuccessListener {
                            name = ""
                            description = ""
                            date = ""
                            time = ""
                            Toast.makeText(context, "Record Inserted", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            println("Failure adding goal")
                        }
                } else{
                    Toast.makeText(context, "Please insert values first", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text(text = "Submit", fontSize = 18.sp)
            }

        }
    }
}





