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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.procrasticure.data.model.SubGoal
import com.example.procrasticure.viewModels.SubGoalsViewModel
import com.example.procrasticure.widgets.GoalMenu
import com.example.procrasticure.widgets.CustomIcon
import com.example.procrasticure.widgets.SubGoalsDisplay
import com.google.firebase.firestore.FirebaseFirestore

// Goal + Subgoal

@Composable
fun SubGoalsScreen(navController: NavController, goalId: String?, goalName: String?) {
    var subGoalsViewModel = goalId?.let { SubGoalsViewModel(it) }

    Column {
        GoalMenu(heading = goalName!!, arrowBackClicked = { navController.popBackStack() })
        if (goalId != null) {
            DisplayMainGoal(navController = navController, goalName = "$goalName") /*TODO: Überschrift anpassen*/
        }
        if (subGoalsViewModel != null) {
            SubGoalList( navController = navController, subGoalsViewModel = subGoalsViewModel)
        }
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
fun DisplayMainGoal(navController: NavController, goalName: String) {

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
                navController.navigate(Screen.AddSubGoalScreen.route)
            }
        }
    }
}

@Composable
fun SubGoalList(
    navController: NavController,
    subGoalsViewModel: SubGoalsViewModel
) {
    var subGoalListState = remember {subGoalsViewModel.subGoals}
    LazyColumn(modifier = Modifier.size(580.dp)) {
        items(items = subGoalListState) { subgoal ->
            var checkedState = remember { mutableStateOf(false) }
            SubGoalsDisplay(
                subgoal = subgoal,
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

            }
        }
    }

}
