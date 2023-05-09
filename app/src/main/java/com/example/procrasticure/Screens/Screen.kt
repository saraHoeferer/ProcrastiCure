package com.example.procrasticure.Screens

sealed class Screen (val route: String){
    object MainScreen: Screen("main")
}