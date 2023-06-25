package com.example.procrasticure.screens


import android.annotation.SuppressLint
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.procrasticure.R
import com.example.procrasticure.data.model.Goal
import com.example.procrasticure.data.notifications.FCMMessage
import com.example.procrasticure.viewModels.BigViewModel
import com.example.procrasticure.viewModels.GoalsViewModel
import com.example.procrasticure.viewModels.UserViewModel
import com.example.procrasticure.widgets.CustomIcon
import com.example.procrasticure.widgets.GoalsDisplay
import com.example.procrasticure.widgets.TopHomeMenu
import kotlinx.coroutines.launch

@Composable
fun GoalsScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    sessionViewModel: BigViewModel,
    goalsViewModel: GoalsViewModel
) {
    val colorPrimary = Color(98, 0, 238)
    val colorDisabled = Color(87, 87, 87, 13)
    val coroutineScope = rememberCoroutineScope()

    var displayState by remember {
        mutableStateOf(true)
    }

    var buttonColorCurrent by remember {
        mutableStateOf(colorPrimary)
    }

    var buttonColorFinished by remember {
        mutableStateOf(colorDisabled)
    }

    var option by remember {
       mutableStateOf(3)
    }

    val goalListState by goalsViewModel.goalsState.collectAsState()
    Column {
        TopHomeMenu(
            navController = navController,
            userViewModel = userViewModel,
            sessionViewModel = sessionViewModel
        )

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
        Row {
            Button(
                onClick = {
                    option = 0
                    coroutineScope.launch { goalsViewModel.sortGoalsByName(sessionViewModel = sessionViewModel) }
                }, colors = if (option != 0) {
                    ButtonDefaults.buttonColors(backgroundColor = Color.White)
                } else {
                    ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                },
                modifier = Modifier.padding(2.dp)
            )
            {
                Text(text = "Name Asc")
            }
            Spacer(modifier = Modifier.weight(.5f))
            Button(
                onClick = {
                    option = 1
                    coroutineScope.launch {
                        goalsViewModel.sortGoalsByDeadlineAscending(
                            sessionViewModel = sessionViewModel
                        )
                    }
                }, colors = if (option != 1) {
                    ButtonDefaults.buttonColors(backgroundColor = Color.White)
                } else {
                    ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                },
                modifier = Modifier.padding(2.dp)
            )
            {
                Text(text = "Date Asc")
            }
            Spacer(modifier = Modifier.weight(.5f))
            Button(
                onClick = {
                    option = 2
                    coroutineScope.launch {
                        goalsViewModel.sortGoalsByDeadlineDescending(
                            sessionViewModel = sessionViewModel
                        )
                    }
                }, colors = if (option != 2) {
                    ButtonDefaults.buttonColors(backgroundColor = Color.White)
                } else {
                    ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                },
                modifier = Modifier.padding(2.dp)
            ) {
                Text(text = "Date Desc")
            }
            Spacer(modifier = Modifier.weight(.5f))
            Button(
                onClick = { option = 3 },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_filter_alt_24),
                    contentDescription = "filter menu",
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }

        if (displayState) {

            GoalList(
                navController = navController,
                goalListState = goalListState
            )
            buttonColorFinished = colorDisabled
            buttonColorCurrent = colorPrimary

        } else {
            FinishedGoalList(goalListState = goalListState)
            buttonColorCurrent = colorDisabled
            buttonColorFinished = colorPrimary
        }
    }
}


@Composable
fun FinishedGoalList(goalListState: ArrayList<Goal>) {
        LazyColumn {
            items(items = goalListState) { goal ->
                if (goal.Finished!!) {
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
                            goal.Name?.let {
                                Text(
                                    text = it,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center,
                                )
                            }
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
}

@Composable
fun GoalList(
    navController: NavController,
    goalListState: ArrayList<Goal>

) {
        LazyColumn {

            items(items = goalListState) { goal ->
                if (!goal.Finished!!) {
                    goal.Name?.let {
                        GoalsDisplay(
                            goal = goal,
                            onClick = {
                                navController.navigate(
                                    Screen.SubGoalsScreen.withIdandName(
                                        goal.Id!!,
                                        goal.Name!!,
                                        goal.Points.toString()
                                    )
                                )
                            },
                            onLongClick = { navController.navigate(Screen.ManageGoalsScreen.route) }
                        ) {
                            CustomIcon(
                                icon = Icons.Default.KeyboardArrowRight,
                                description = "Go to Goal Details",
                                color = Color.Gray
                            ) {

                                navController.navigate(
                                    Screen.SubGoalsScreen.withIdandName(
                                        goal.Id!!,
                                        goal.Name!!,
                                        goal.Points.toString()
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
}
