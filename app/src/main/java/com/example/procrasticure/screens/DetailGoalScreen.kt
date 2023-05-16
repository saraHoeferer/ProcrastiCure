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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.procrasticure.R
import com.example.procrasticure.widgets.GoalMenu
// Goal + Subgoal

@Composable
fun DetailScreen(navController: NavController, goalId: String?) {
    var goal = "Goal"
    if (goalId != null) {
        goal = goalId
    }
    val subGoalList = listOf("1# Subgoal", "2# Subgoal", "3# Subgoal")
    Column {
        GoalMenu(heading = "$goal# Goal", arrowBackClicked = { navController.popBackStack() })
        DisplayMainGoal(goalId = goal, navController = navController)
        DisplaySubGoals(goalList = subGoalList)
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
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Goal",
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .width(35.dp)
                    .height(35.dp)
                    .clickable { navController.navigate(Screen.AddSubGoalScreen.route) },
                tint = MaterialTheme.colors.primary,
            )
        }
    }
}

@Composable
fun DisplaySubGoals(goalList: List<String>) {
    LazyColumn() {
        items(items = goalList) { goal ->
            var checkedState = remember { mutableStateOf(false) }
            Box(contentAlignment = Alignment.TopEnd) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 3.dp)
                            .clip(shape = RoundedCornerShape(18.dp))
                            .background(Color(87, 87, 87, 22))
                    ) {
                        Row(
                            modifier = Modifier
                                .width(400.dp)
                                .padding(0.dp)
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
                                    .clip(shape = RoundedCornerShape(100.dp))
                            )
                            Text(
                                text = goal,
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(horizontal = 0.dp, vertical = 11.dp)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = "Edit Goal",
                                modifier = Modifier
                                    .padding(8.dp)
                                    .width(30.dp)
                                    .height(30.dp),
                                tint = Color.DarkGray
                            )
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "delete",
                                modifier = Modifier
                                    .padding(8.dp)
                                    .width(30.dp)
                                    .height(30.dp),
                            )
                        }
                    }
            }
        }
    }
}
