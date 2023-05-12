package com.example.procrasticure.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.procrasticure.Widgets.TopMenu

// Goal + Subgoal

@Composable
fun DetailScreen(navController: NavController){
    Column() {
        TopMenu(navController = navController)
        Text(text = "Details Screen")
    }
}