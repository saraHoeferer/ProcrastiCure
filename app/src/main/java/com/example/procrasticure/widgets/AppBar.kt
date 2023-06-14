package com.example.procrasticure.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.procrasticure.screens.Screen
import com.example.procrasticure.viewModels.BigViewModel
import com.example.procrasticure.viewModels.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun TopMenu(arrowBackClicked: () -> Unit = {}, heading: String){
    TopAppBar(elevation = 2.dp) {
        Row(Modifier.padding(10.dp)) {
            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = "Arrow back",
                modifier = Modifier.clickable {
                    arrowBackClicked()
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(text = heading, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
@Composable
fun GoalMenu(arrowBackClicked: () -> Unit = {}, heading: String){
    TopAppBar(elevation = 2.dp) {
        Row(Modifier.padding(10.dp)) {
            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = "Arrow back",
                modifier = Modifier.clickable {
                    arrowBackClicked()
                }
            )
            Spacer(modifier = Modifier.weight(1.6f))
            Text(text = heading, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "edit",
                modifier = Modifier
                    .size(30.dp)
            )
            Spacer(modifier = Modifier.weight(0.1f))
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "delete",
                modifier = Modifier
                    .size(30.dp)
            )
        }
    }
}

@Composable
fun TopHomeMenu(navController: NavController, userViewModel: UserViewModel, sessionViewModel: BigViewModel) {
    var showMenu by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
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
                    DropdownMenuItem(onClick = { navController.navigate(Screen.TimerScreen.route) }) {
                        Text(text = "Timer")
                    }

                    DropdownMenuItem(onClick = {
                        coroutineScope.launch { userViewModel.logout(sessionViewModel) }
                        navController.navigate(Screen.Login.route)
                    }) {
                        Text(text = "LogOut")
                    }
                    DropdownMenuItem(onClick = { navController.navigate(Screen.AnimalScreen.route) }) {
                        Text(text = "Animals")
                    }
                }
            }
        }
    }
}



