package com.example.procrasticure.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.procrasticure.Widgets.TopMenu

// add a subgoal

@Composable
fun AddSubGoalScreen(navController: NavController){
    Column() {
        TopMenu(navController = navController, arrowBackClicked = { navController.popBackStack() })
        // call details here
        Text(text = "Add Sub Goals")
    }
}