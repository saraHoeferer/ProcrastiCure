package com.example.procrasticure.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.procrasticure.Screens.*
import com.example.procrasticure.ViewModels.TimerViewModel

val goalList = listOf("Goal #1", "Goal #2", "Goal #3")
@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route){
        composable(route = Screen.MainScreen.route){
            HomeScreen(movieList = goalList, navController = navController)
        }

        composable(route = Screen.DetailScreen.route){
            DetailScreen(navController = navController)
        }

        composable(route = Screen.ProfileScreen.route){
            ProfileScreen(navController = navController)
        }

        composable(route = Screen.TimerScreen.route){
            Timer(navController = navController)
        }

        composable(route = Screen.AddGoalScreen.route){
            AddGoalScreen(navController = navController)
        }

        composable(route = Screen.AddSubGoalScreen.route){
            AddSubGoalScreen(navController = navController)
        }
    }
}