package com.example.procrasticure.Widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.procrasticure.Screens.Screen

@Composable
fun TopMenu(navController: NavController){
    var showMenu by remember { mutableStateOf(false) }
    androidx.compose.material.TopAppBar(elevation = 2.dp) {
        Row() {
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
                }
            }
        }
    }
}