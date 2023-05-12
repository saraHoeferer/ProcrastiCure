package com.example.procrasticure.Widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.procrasticure.Screens.Screen

@Composable
fun TopMenu(navController: NavController, arrowBackClicked: () -> Unit = {}){
    var showMenu by remember { mutableStateOf(false) }
    TopAppBar(elevation = 2.dp) {
        Row(Modifier.padding(5.dp)) {
            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = "Arrow back",
                modifier = Modifier.clickable {
                    arrowBackClicked() // wenn der Up Button geklickt wird
                }
            )
            Text(text = "ProcrastiCure", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Box(contentAlignment = Alignment.TopEnd) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "menu",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { showMenu = !showMenu })
                DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                    DropdownMenuItem(onClick = { navController.navigate(Screen.ProfileScreen.route) }) {
                        Text(text = "Profile")
                    }
                    DropdownMenuItem(onClick = { navController.navigate(Screen.DetailScreen.route) }) {
                        Text(text = "Details")
                    }
                    DropdownMenuItem(onClick = { navController.navigate(Screen.MainScreen.route) }) {
                        Text(text = "Home")
                    }
                    DropdownMenuItem(onClick = { navController.navigate(Screen.TimerScreen.route) }) {
                        Text(text = "Timer")
                    }
                    DropdownMenuItem(onClick = { navController.navigate(Screen.AddGoalScreen.route)}) {
                        Text(text = "Add New Goal")
                    }
                }
            }
        }
    }
}

@Composable
fun TopHomeMenu(navController: NavController){
    var showMenu by remember { mutableStateOf(false) }
    TopAppBar(elevation = 2.dp) {
        Row(Modifier.padding(10.dp)) {
            Text(text = "ProcrastiCure", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Box(contentAlignment = Alignment.TopEnd) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "menu",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { showMenu = !showMenu })
                DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                    DropdownMenuItem(onClick = { navController.navigate(Screen.ProfileScreen.route) }) {
                        Text(text = "Profile")
                    }
                    DropdownMenuItem(onClick = { navController.navigate(Screen.DetailScreen.route) }) {
                        Text(text = "Details")
                    }
                    DropdownMenuItem(onClick = { navController.navigate(Screen.MainScreen.route) }) {
                        Text(text = "Home")
                    }
                    DropdownMenuItem(onClick = { navController.navigate(Screen.TimerScreen.route) }) {
                        Text(text = "Timer")
                    }
                    DropdownMenuItem(onClick = { navController.navigate(Screen.AddGoalScreen.route)}) {
                        Text(text = "Add New Goal")
                    }
                }
            }
        }
    }
}



