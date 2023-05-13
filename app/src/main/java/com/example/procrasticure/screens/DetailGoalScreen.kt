package com.example.procrasticure.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.procrasticure.widgets.TopMenu

// Goal + Subgoal

@Composable
fun DetailScreen(navController: NavController){
    Column() {
        TopMenu(navController = navController, arrowBackClicked = { navController.popBackStack() },
            heading = "Subgoals")
        Text(text = "Details Screen")
    }
}