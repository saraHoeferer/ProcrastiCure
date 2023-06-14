package com.example.procrasticure.screens


import androidx.compose.foundation.background

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.procrasticure.viewModels.GoalsViewModel
import com.example.procrasticure.widgets.TopHomeMenu
import com.example.procrasticure.widgets.CustomIcon

import com.example.procrasticure.widgets.GoalsDisplay

@Composable
fun GoalsScreen(navController: NavController) {
    val colorPrimary = Color(98, 0, 238)
    val colorDisabled = Color(87, 87, 87, 13)

    val goalsViewModel: GoalsViewModel = viewModel()

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
                onClick = {
                    displayState = true

                },
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
                CustomIcon(
                    icon = Icons.Default.Add,
                    description = "Add Goal",
                    color = MaterialTheme.colors.primary
                ) {
                    navController.navigate(Screen.AddGoalScreen.route)
                }
            }
        }

        if (displayState) {
            GoalList(navController = navController, goalsViewModel = goalsViewModel)
            buttonColorFinished = colorDisabled
            buttonColorCurrent = colorPrimary

        } else {
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
fun GoalList(
    navController: NavController,
    goalsViewModel: GoalsViewModel

) {
    val goalListState = remember {goalsViewModel.goals}


    LazyColumn {

        items(items = goalListState) { goal ->
            goal.Name?.let {
                GoalsDisplay(
                    goalName = it,
                    onClick = { navController.navigate(Screen.SubGoalsScreen.withIdandName(goal.getId()!!,
                        goal.Name!!
                    )) },
                    onLongClick = { navController.navigate(Screen.ManageGoalsScreen.route) }
                ) {
                    CustomIcon(
                        icon = Icons.Default.KeyboardArrowRight,
                        description = "Go to Goal Details",
                        color = Color.Gray
                    ) {

                        navController.navigate(Screen.SubGoalsScreen.withIdandName(goal.getId()!!, goal.Name!!))
                    }
                }
            }
        }
    }
}

