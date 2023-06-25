package com.example.procrasticure.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.procrasticure.data.model.Goal
import com.example.procrasticure.data.repository.GoalRepositoryImpl
import com.example.procrasticure.viewModels.BigViewModel
import com.example.procrasticure.viewModels.GoalsViewModel
import com.example.procrasticure.widgets.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun ManageGoalsScreen(navController: NavController, sessionViewModel: BigViewModel, goalsRepository: GoalRepositoryImpl) {
    var goalsViewModel = GoalsViewModel(sessionViewModel, goalsRepository)

    Column {
        TopMenu(heading = "Editing Goals", arrowBackClicked = { navController.popBackStack() })
        EditingGoalsDisplay(goalsViewModel, navController)
    }
}

@Composable
fun EditingGoalsDisplay(goalsViewModel: GoalsViewModel, navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val goalListState = remember {goalsViewModel.goals}
    LazyColumn {
        items(items = goalListState ){ goal ->
            if (!goal.Finished!!) {
                goal.Name?.let {
                    goal.Date?.let { it1 ->
                        GoalsDisplay(goal = goal) {
                            CustomIcon(
                                icon = Icons.Outlined.Edit,
                                description = "Edit Goal",
                                clickEvent = { navController.navigate(Screen.UpdateGoalScreen
                                    .withDetails(
                                        name = goal.Name!!,
                                        description = goal.Description!!,
                                        date = goal.Date!!,
                                        time = goal.Time!!,
                                        goalID = goal.Id!!)) })

                            CustomIcon(
                                icon = Icons.Default.Delete,
                                description = "Delete Goal",
                                clickEvent = {
                                    Log.d("GOAl ID", goal.Id.toString())
                                    coroutineScope.launch {
                                        goal.Id?.let { it2 -> goalsViewModel.deleteGoal(goalId = it2, context) }
                                        navController.navigate(Screen.GoalsScreen.route)
                                    }
                                    deleteGoal(courseID = goal.Id, context = context)
                                })
                        }
                    }
                }
            }
        }
    }
}


//deletes Goals without deleting the SubGoals
private fun deleteGoal(courseID: String?, context: Context) {

    val db = FirebaseFirestore.getInstance();

    deleteSubGoals(courseID)

    db.collection("Goals")
        .document(courseID.toString())
        .delete()
        .addOnSuccessListener { Toast.makeText(context, "Goal was deleted successfully..", Toast.LENGTH_SHORT).show() }
        .addOnFailureListener { Toast.makeText(context, "Fail to delete goal..", Toast.LENGTH_SHORT).show() }

}

private fun updateDataToFirebase(
    id: String?,
    date: String?,
    description: String?,
    name: String?,
    time: String?,
    finished: Boolean,
    context: Context
) {
    val updateGoal = Goal(Id = id, Date = date, Name = name, Description = description, Time = time, Finished = finished)

    val db = FirebaseFirestore.getInstance();
    db.collection("Goals").document(id.toString()).set(updateGoal)
        .addOnSuccessListener {
            Toast.makeText(context, "Goal is updated successfully..", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener {
            Toast.makeText(context, "Fail to update goal : " + it.message, Toast.LENGTH_SHORT)
                .show()
        }
}


