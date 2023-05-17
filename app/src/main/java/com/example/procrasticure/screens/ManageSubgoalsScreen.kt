package com.example.procrasticure.screens

import androidx.compose.material.icons.outlined.Edit
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.procrasticure.widgets.CustomIcon
import com.example.procrasticure.widgets.SubGoalsDisplay
import com.example.procrasticure.widgets.TopMenu

@Composable
fun ManageSubgoalsScreen(navController: NavController) {
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
        TopMenu(heading = "Editing Subgoals", arrowBackClicked = { navController.popBackStack() })
        EditingSubgoalsDisplay(goalList = subGoalList)
    }
}

@Composable
fun EditingSubgoalsDisplay(goalList: List<String>) {
    LazyColumn() {
        items(items = goalList) { goal ->
            SubGoalsDisplay{
                Row {
                    Text(
                        text = goal,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 11.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    CustomIcon(
                        icon = Icons.Outlined.Edit,
                        description = "Edit a specific SubGoal",
                        size = 30
                    )
                    CustomIcon(
                        icon = Icons.Default.Delete,
                        description = "Delete a specific SubGoal",
                        size = 30
                    )
                }
            }
        }
    }
}