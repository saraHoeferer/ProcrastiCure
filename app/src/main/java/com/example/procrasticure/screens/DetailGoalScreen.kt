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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.procrasticure.widgets.GoalMenu
import com.example.procrasticure.widgets.CustomIcon
import com.example.procrasticure.widgets.SubGoalsDisplay

// Goal + Subgoal

@Composable
fun DetailScreen(navController: NavController, goalId: String?) {
    var goal = "Goal"
    if (goalId != null) {
        goal = goalId
    }
    val subGoalList = listOf(
        "1# Subgoal",
        "2# Subgoal",
        "3# Subgoal",
        "4# Subgoal",
        "5# Subgoal",
        "6# Subgoal",
        "7# Subgoal",
        "8# Subgoal",
        "9# Subgoal",
        "10# Subgoal",
        "11# Subgoal",
        "12# Subgoal",
        "13# Subgoal"
    )
    Column {
        GoalMenu(heading = "$goal# Goal", arrowBackClicked = { navController.popBackStack() })
        DisplayMainGoal(goalId = goal, navController = navController)
        DisplaySubGoals(goalList = subGoalList, navController = navController)
        FinishGoal()
    }
}

@Composable
fun FinishGoal() {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
        Button(
            onClick = { /*TODO*/ }, modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp), colors = ButtonDefaults.buttonColors(Color.Gray)
        ) {
            Text(text = "Finish Goal")
        }
    }
}

@Composable
fun DisplayMainGoal(goalId: String, navController: NavController) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
    )
    {
        Row() {
            Text(
                fontWeight = FontWeight.Bold,
                text = "$goalId# Goal",
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
                navController.navigate(Screen.AddSubGoalScreen.route)
            }
        }
    }
}

@Composable
fun DisplaySubGoals(goalList: List<String>, navController: NavController) {
    LazyColumn(modifier = Modifier.size(580.dp)) {
        items(items = goalList) { goal ->
            var checkedState = remember { mutableStateOf(false) }
            SubGoalsDisplay(
                onClick = { navController.navigate(Screen.AddSubGoalScreen.route) },
                onLongClick = { navController.navigate(Screen.ManageSubGoalsScreen.route) }) {
                Checkbox(
                    checked = checkedState.value,
                    onCheckedChange = { checkedState.value = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colors.primary,
                        uncheckedColor = Color.Gray,
                        checkmarkColor = Color.White
                    ),
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(100.dp))
                )
                Text(
                    text = goal,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 0.dp, vertical = 11.dp)
                )
            }
        }
    }
}
