package com.example.procrasticure.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.procrasticure.R
import com.example.procrasticure.widgets.TopHomeMenu

@Composable
fun HomeScreen(movieList: List<String>, navController: NavController) {
    val colorPrimary = Color(98, 0, 238)
    val colorDisabled = Color(87,87,87,13)

    var displayState by remember {
        mutableStateOf(true)
    }

    var buttonColorCurrent by remember {
        mutableStateOf(colorPrimary)
    }

    var buttonColorFinished by remember {
        mutableStateOf(colorDisabled)
    }
    Column {
        TopHomeMenu(navController = navController)
        Row {
            Button(
                onClick = { displayState = true },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = buttonColorCurrent),
                modifier = Modifier
                    .padding(4.dp)
            ) {
                Text(text = "Current Goals")
            }
            Button(
                onClick = { displayState = false },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = buttonColorFinished),
                modifier = Modifier
                    .padding(4.dp)
            ) {
                Text(text = "Finished Goals")
            }

            if (displayState) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Goal",
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .width(35.dp)
                        .height(35.dp)
                        .clickable { navController.navigate(Screen.AddGoalScreen.route)},
                    tint = colorPrimary,
                )
            }
        }

        if (displayState) {
            GoalList(goalList = movieList, navController=navController)
            buttonColorFinished = colorDisabled
            buttonColorCurrent = colorPrimary

        } else {
            FinishedGoalList(goalList = movieList)
            buttonColorCurrent = colorDisabled
            buttonColorFinished = colorPrimary
        }
    }

}


@Composable
fun FinishedGoalList(goalList: List<String>) {
    LazyColumn {
        items(items = goalList) { goal ->
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .clip(shape = RoundedCornerShape(18.dp))
                    .background(Color(87, 87, 87, 22))
                    .width(400.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 18.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = goal,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = "Points earned: 200",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun GoalList(goalList: List<String>, navController: NavController) {
    LazyColumn {
        items(items = goalList) { goal ->
            // check State for checkbox
            val checkedState = remember { mutableStateOf(false) }
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .clip(shape = RoundedCornerShape(18.dp))
                    .background(Color(87, 87, 87, 22))
            ) {
                Row(
                    modifier = Modifier
                        .width(400.dp)
                        .padding(5.dp)
                ) {
                    Checkbox(
                        checked = checkedState.value,
                        onCheckedChange = { checkedState.value = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colors.primary,
                            uncheckedColor = Color.Gray,
                            checkmarkColor = Color.White
                        ),
                        modifier = Modifier
                            .padding(horizontal = 0.dp, vertical = 2.dp)
                    )
                    Text(
                        text = goal,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(horizontal = 2.dp, vertical = 12.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Go to Goal Details",
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 10.dp)
                            .width(35.dp)
                            .height(35.dp)
                            .clickable { navController.navigate(Screen.DetailScreen.withId(goal))},
                        tint = Color.Gray,
                    )

                    // Checkbox
                    /*Checkbox(
                        checked = checkedState.value,
                        onCheckedChange = { checkedState.value = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.Green,
                            uncheckedColor = Color.Gray,
                            checkmarkColor = Color.White
                        ),
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(100.dp))
                    )*/

                    // Edit Icon
                    /*Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Edit Goal",
                        modifier = Modifier
                            .padding(8.dp)

                            .width(30.dp)
                            .height(30.dp),
                        tint = Color.DarkGray
                    )
                    */
                }
            }
        }
    }
}