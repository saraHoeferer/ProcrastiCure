package com.example.procrasticure.Screens

sealed class Screen (val route: String){
    object MainScreen: Screen("main")
    object ProfileScreen: Screen("profile")

    object DetailScreen: Screen("details")
}