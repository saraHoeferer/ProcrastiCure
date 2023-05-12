package com.example.procrasticure.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.procrasticure.Widgets.TopHomeMenu
import com.example.procrasticure.Widgets.TopMenu

@Composable
fun HomeScreen(movieList: List<String>, navController: NavController){
    Column() {
        TopHomeMenu(navController = navController)
        heading(text = "Current Goals")
        goalList(goalList = movieList, checked = false)
        heading(text = "Finished goals")
        goalList(goalList = movieList, checked = true)
    }

}


@Composable
fun heading(text: String){
    Text(text = text,
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 12.dp),
        textAlign = TextAlign.Center)
}

@Composable
fun goalList(goalList: List<String>, checked: Boolean){
    LazyColumn() {
        items(items = goalList) { goal ->
            val checkdState = remember { mutableStateOf(checked) }
            Row (modifier = Modifier){
                Checkbox(checked = checkdState.value, onCheckedChange = {checkdState.value = it})
                Text(text = goal, fontSize = 20.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 1.dp, vertical = 10.dp))
            }
            Divider(startIndent = 8.dp, thickness = 1.dp, color = Color.Black)
        }
    }
}