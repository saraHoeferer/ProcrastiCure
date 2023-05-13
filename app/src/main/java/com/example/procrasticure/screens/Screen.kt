package com.example.procrasticure.screens

sealed class Screen (val route: String){
    object MainScreen: Screen("main")
    object ProfileScreen: Screen("profile")

    object DetailScreen: Screen("details")

    object TimerScreen: Screen("timer")

    object AddGoalScreen: Screen("addGoal")

    object AddSubGoalScreen: Screen("addSubGoal")
}