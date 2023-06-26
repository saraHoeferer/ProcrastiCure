package com.example.procrasticure.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.procrasticure.data.repository.GoalRepositoryImpl
import com.example.procrasticure.viewModels.BigViewModel
import com.example.procrasticure.viewModels.GoalsViewModel
import com.example.procrasticure.widgets.CustomIcon
import com.example.procrasticure.widgets.GoalsDisplay
import com.example.procrasticure.widgets.TopMenu
import kotlinx.coroutines.launch

@Composable
fun ManageGoalsScreen(
    navController: NavController,
    sessionViewModel: BigViewModel,
    goalsRepository: GoalRepositoryImpl
) {
    val goalsViewModel = GoalsViewModel(sessionViewModel, goalsRepository)

    Column {
        TopMenu(heading = "Editing Goals", arrowBackClicked = { navController.popBackStack() })
        EditingGoalsDisplay(goalsViewModel, navController)
    }
}

@Composable
fun EditingGoalsDisplay(goalsViewModel: GoalsViewModel, navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val goalListState = remember { goalsViewModel.goals }
    LazyColumn {
        items(items = goalListState) { goal ->
            if (!goal.Finished!!) {
                goal.Name?.let {
                    goal.Date?.let { it1 ->
                        GoalsDisplay(goal = goal) {
                            CustomIcon(
                                icon = Icons.Outlined.Edit,
                                description = "Edit Goal",
                                clickEvent = {
                                    navController.navigate(
                                        Screen.UpdateGoalScreen
                                            .withDetails(
                                                name = goal.Name!!,
                                                description = goal.Description!!,
                                                date = goal.Date!!,
                                                time = goal.Time!!,
                                                goalID = goal.Id!!
                                            )
                                    )
                                })

                            CustomIcon(
                                icon = Icons.Default.Delete,
                                description = "Delete Goal",
                                clickEvent = {
                                    Log.d("GOAl ID", goal.Id.toString())
                                    coroutineScope.launch {
                                        goal.Id?.let { it2 ->
                                            goalsViewModel.deleteGoal(
                                                goalId = it2,
                                                context
                                            )
                                        }
                                        navController.navigate(Screen.GoalsScreen.route)
                                    }
                                })
                        }
                    }
                }
            }
        }
    }
}


