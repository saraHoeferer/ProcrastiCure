package com.example.procrasticure.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.procrasticure.Widgets.TopMenu

// add a goal

@Composable
fun AddGoalScreen(navController: NavController){
    Column() {
        TopMenu(navController = navController, arrowBackClicked = { navController.popBackStack() })
        // details hier callen
        Text(text = "addgoals")
    }
}