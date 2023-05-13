package com.example.procrasticure.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.procrasticure.R
import com.example.procrasticure.widgets.TopHomeMenu

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
            val checkedState = remember { mutableStateOf(checked) }
            Box() {
                Row(modifier = Modifier.width(400.dp)) {
                    Checkbox(
                        checked = checkedState.value,
                        onCheckedChange = { checkedState.value = it })
                    Text(
                        text = goal,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 1.dp, vertical = 10.dp)
                    )

                    Column() {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = "Edit Goal",
                            modifier = Modifier
                                .padding(8.dp)

                                .width(30.dp)
                                .height(30.dp),
                            tint = Color.DarkGray
                        )
                    }

                    Spacer(modifier = Modifier.padding(horizontal = 0.dp))
                    Column(horizontalAlignment = Alignment.End) {


                        Icon(
                            painter = painterResource(id = R.drawable.baseline_format_list_bulleted_24),
                            contentDescription = "Display Subgoals",
                            modifier = Modifier
                                .padding(8.dp)
                                .width(30.dp)
                                .height(30.dp),
                            tint = Color.DarkGray,
                        )
                    }
                }
                    Divider(startIndent = 8.dp, thickness = 1.dp, color = Color.Black)
                }

        }
    }
}