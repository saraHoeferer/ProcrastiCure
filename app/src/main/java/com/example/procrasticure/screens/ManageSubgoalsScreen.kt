package com.example.procrasticure.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.procrasticure.data.repository.SubGoalRepositoryImpl
import com.example.procrasticure.viewModels.SubGoalsViewModel
import com.example.procrasticure.widgets.CustomIcon
import com.example.procrasticure.widgets.SubGoalsDisplay
import com.example.procrasticure.widgets.TopMenu
import kotlinx.coroutines.launch

@Composable
fun ManageSubGoalsScreen(
    navController: NavController,
    goalID: String?,
    goalPoints: String?,
    goalRepository: SubGoalRepositoryImpl
) {
    val subGoalsViewModel: SubGoalsViewModel =
        goalID?.let { SubGoalsViewModel(it, goalRepository) }!!

    Column {
        TopMenu(heading = "Editing Subgoals", arrowBackClicked = { navController.popBackStack() })
        EditingSubgoalsDisplay(
            subGoalsViewModel = subGoalsViewModel,
            goalID = goalID,
            navController = navController,
            goalPoints = goalPoints!!.toLong()
        )
    }
}

@Composable
fun EditingSubgoalsDisplay(
    subGoalsViewModel: SubGoalsViewModel,
    goalID: String,
    goalPoints: Long,
    navController: NavController
) {
    val goalListState = remember { subGoalsViewModel.subGoals }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    LazyColumn {
        items(items = goalListState) { subgoal ->
            SubGoalsDisplay(subgoal = subgoal) {
                Row {
                    Text(
                        text = subgoal.Name!!,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 11.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    CustomIcon(
                        icon = Icons.Outlined.Edit,
                        description = "Edit a specific SubGoal",
                        size = 30,
                        clickEvent = {
                            Log.d("GOALID", goalID)
                            Log.d("SUBGOALID", subgoal.Id.toString())

                            navController.navigate(
                                Screen.UpdateSubGoalScreen
                                    .withDetails(
                                        goalID = goalID,
                                        name = subgoal.Name!!,
                                        description = subgoal.Description!!,
                                        date = subgoal.Date!!,
                                        time = subgoal.Time!!,
                                        subGoalID = subgoal.Id!!
                                    )
                            )
                        }
                    )
                    CustomIcon(
                        icon = Icons.Default.Delete,
                        description = "Delete a specific SubGoal",
                        size = 30,
                        clickEvent = {
                            coroutineScope.launch {
                                subGoalsViewModel.deleteSubGoal(
                                    goalID, goalPoints, subgoal.Id!!,
                                    subgoal.Finished!!, context
                                )
                                navController.popBackStack()
                                navController.navigate(Screen.GoalsScreen.route)
                            }
                        }
                    )
                }
            }
        }
    }
}