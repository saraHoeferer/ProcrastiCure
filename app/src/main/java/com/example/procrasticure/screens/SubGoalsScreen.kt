package com.example.procrasticure.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.procrasticure.data.model.Goal
import com.example.procrasticure.data.repository.SubGoalRepositoryImpl
import com.example.procrasticure.viewModels.BigViewModel
import com.example.procrasticure.viewModels.GoalsViewModel
import com.example.procrasticure.viewModels.SubGoalsViewModel
import com.example.procrasticure.widgets.GoalMenu
import com.example.procrasticure.widgets.CustomIcon
import com.example.procrasticure.widgets.SubGoalsDisplay
import kotlinx.coroutines.launch

// Goal + Subgoal

@Composable
fun SubGoalsScreen(
    navController: NavController,
    goalId: String?,
    goalName: String?,
    goalPoints: String?,
    goalsViewModel: GoalsViewModel,
    sessionViewModel: BigViewModel,
    subGoalRepository: SubGoalRepositoryImpl
) {
    val subGoalsViewModel = SubGoalsViewModel(goalId!!, subGoalRepository)
    val subGoalListState = remember { subGoalsViewModel.subGoals }
    println(subGoalListState)
    Column {
        GoalMenu(heading = goalName!!, arrowBackClicked = { navController.popBackStack() })
        DisplayMainGoal(navController = navController, goalName = "$goalName", goalId = goalId)
        if (goalPoints != null) {
            SubGoalList(
                navController = navController,
                subGoalsViewModel = subGoalsViewModel,
                goalId = goalId,
                goalPoints = goalPoints.toLong(),
                subGoalListState = subGoalListState,
                goalName = goalName
            )
        }
        FinishGoal(
            subGoalListState,
            goalId = goalId,
            goalPoints = goalPoints!!.toLong(),
            goalsViewModel = goalsViewModel,
            sessionViewModel = sessionViewModel,
            navController
        )
    }
}

@Composable
fun FinishGoal(
    subGoalListState: ArrayList<Goal>,
    goalId: String,
    goalPoints: Long,
    goalsViewModel: GoalsViewModel,
    sessionViewModel: BigViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
        val coroutineScope = rememberCoroutineScope()
        val enabled = remember { mutableStateOf(true) }
        Button(
            onClick = {
                if (subGoalListState.isNotEmpty()) {
                    for (items in subGoalListState) {
                        if (items.Finished == false) {
                            enabled.value = false
                        }
                    }
                } else {
                    enabled.value = true
                }
                if (enabled.value) {
                    coroutineScope.launch {
                        goalsViewModel.finishGoal(
                            goalId = goalId,
                            goalPoints = goalPoints,
                            sessionViewModel = sessionViewModel
                        )
                        navController.popBackStack()
                        navController.navigate(Screen.GoalsScreen.route)
                    }
                } else {
                    Toast.makeText(
                        context,
                        "You must finish all subgoals before you can finish the mainGoal",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp), colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary)
        ) {
            Text(text = "Finish Goal")
        }
    }
}

@Composable
fun DisplayMainGoal(navController: NavController, goalName: String, goalId: String) {

    Box(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
    )
    {
        Row {
            Text(
                fontWeight = FontWeight.Bold,
                text = goalName,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 18.dp, vertical = 11.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            CustomIcon(
                icon = Icons.Default.Add,
                description = "Add Subgoal",
                color = MaterialTheme.colors.primary
            ) {
                navController.navigate(Screen.AddSubGoalScreen.withGoalId(goalId))
            }
        }
    }
}

@Composable
fun SubGoalList(
    navController: NavController,
    subGoalsViewModel: SubGoalsViewModel,
    goalId: String,
    goalPoints: Long,
    goalName: String,
    subGoalListState: ArrayList<Goal>
) {
    var goalP = goalPoints
    LazyColumn(modifier = Modifier.size(580.dp)) {
        items(items = subGoalListState) { subgoal ->
            val coroutineScope = rememberCoroutineScope()
            val checked = remember { mutableStateOf(subgoal.Finished) }
            SubGoalsDisplay(
                subgoal = subgoal,
                onLongClick = {
                    navController.navigate(
                        Screen.ManageSubGoalsScreen.withGoalID(
                            goalId,
                            goalPoints.toString()
                        )
                    )
                }
            ) {
                Checkbox(
                    checked = checked.value!!,
                    onCheckedChange = {
                        if (checked.value == true) {
                            checked.value = it
                            coroutineScope.launch {
                                subgoal.Id
                                    ?.let { it1 ->
                                        goalP = subGoalsViewModel.uncheckSubGoal(
                                            goalId = goalId,
                                            subGoalId = it1,
                                            goalPoints = goalP
                                        )
                                    }
                                navController.popBackStack(Screen.GoalsScreen.route, false)
                                navController.navigate(
                                    Screen.SubGoalsScreen.withIdandName(
                                        goalId,
                                        goalName,
                                        goalP.toString()
                                    )
                                )
                            }

                        } else {
                            checked.value = it
                            coroutineScope.launch {
                                subgoal.Id
                                    ?.let { it1 ->
                                        goalP = subGoalsViewModel.checkSubGoal(
                                            goalId = goalId,
                                            subGoalId = it1,
                                            goalPoints = goalP
                                        )
                                    }
                                navController.popBackStack(Screen.GoalsScreen.route, false)
                                navController.navigate(
                                    Screen.SubGoalsScreen.withIdandName(
                                        goalId,
                                        goalName,
                                        goalP.toString()
                                    )
                                )
                            }


                        }
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colors.primary,
                        uncheckedColor = Color.Gray,
                        checkmarkColor = Color.White
                    ),
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(100.dp))
                )
                Text(
                    text = subgoal.Name!!,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 0.dp, vertical = 11.dp)
                )

            }
        }
    }

}
