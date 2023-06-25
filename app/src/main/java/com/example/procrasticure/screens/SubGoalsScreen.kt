package com.example.procrasticure.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
fun SubGoalsScreen(navController: NavController, goalId: String?, goalName: String?, goalPoints: String?, goalsViewModel: GoalsViewModel, sessionViewModel: BigViewModel, subGoalRepository: SubGoalRepositoryImpl) {
    var subGoalsViewModel = goalId?.let { SubGoalsViewModel(it, subGoalRepository) }

    Column {
        GoalMenu(heading = goalName!!, arrowBackClicked = { navController.popBackStack() })
        if (goalId != null) {
            goalId?.let { DisplayMainGoal(navController = navController, goalName = "$goalName", goalId= it) } /*TODO: Überschrift anpassen*/
        }
        if (subGoalsViewModel != null) {
            if (goalId != null) {
                if (goalPoints != null) {
                    SubGoalList( navController = navController, subGoalsViewModel = subGoalsViewModel, goalId = goalId, goalPoints = goalPoints.toLong())
                }
            }
        }
        if (subGoalsViewModel != null) {
            if (goalId != null) {
                goalId?.let { FinishGoal(subGoalsViewModel, goalId = it, goalPoints = goalPoints!!.toLong(), goalsViewModel = goalsViewModel, sessionViewModel = sessionViewModel) }
            }
        }
    }
}

@Composable
fun FinishGoal(subGoalsViewModel: SubGoalsViewModel, goalId: String, goalPoints:Long, goalsViewModel: GoalsViewModel, sessionViewModel: BigViewModel) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
        var subGoalListState = remember {subGoalsViewModel.subGoals}
        val coroutineScope = rememberCoroutineScope()
        var enabled = true
        Button(
            onClick = {
                if (subGoalListState.isNotEmpty()) {
                    for (items in subGoalListState) {
                        if (items.Finished == false) {
                            enabled = false
                        }
                    }
                } else {
                    enabled = true
                }
                if (enabled){
                    coroutineScope.launch{
                         goalsViewModel.finishGoal(goalId = goalId, goalPoints = goalPoints, sessionViewModel = sessionViewModel)
                    }
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp), colors = ButtonDefaults.buttonColors(Color.Gray)
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
        Row() {
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
) {
    var subGoalListState = remember {subGoalsViewModel.subGoals}
    var goalP = goalPoints
    LazyColumn(modifier = Modifier.size(580.dp)) {
        items(items = subGoalListState) { subgoal ->
            val coroutineScope = rememberCoroutineScope()
            var checked = remember { mutableStateOf(subgoal.Finished) }
            SubGoalsDisplay(
                subgoal = subgoal,
                onLongClick = { navController.navigate(Screen.ManageSubGoalsScreen.withGoalID(goalId)) }) {
                Checkbox(
                    checked = checked.value!!,
                    onCheckedChange = {
                        if (checked.value == true){
                            subgoal.Finished = false
                            coroutineScope.launch { subgoal.Id
                                ?.let { it1 -> goalP = subGoalsViewModel.uncheckSubGoal(goalId = goalId, subGoalId = it1, goalPoints = goalP) }
                            }

                        } else {
                            subgoal.Finished = true
                            coroutineScope.launch { subgoal.Id
                                ?.let { it1 -> goalP = subGoalsViewModel.checkSubGoal(goalId = goalId, subGoalId = it1, goalPoints = goalP) } }

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

            }
        }
    }

}
