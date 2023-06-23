package com.example.procrasticure.screens

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.procrasticure.data.model.Goal
import com.example.procrasticure.viewModels.GoalsViewModel
import com.example.procrasticure.widgets.*
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

@Composable
fun ManageGoalsScreen(navController: NavController) {
    val goalsViewModel: GoalsViewModel = viewModel()

    Column {
        TopMenu(heading = "Editing Goals", arrowBackClicked = { navController.popBackStack() })
        EditingGoalsDisplay(goalsViewModel, navController)
    }
}

@Composable
fun EditingGoalsDisplay(goalsViewModel: GoalsViewModel, navController: NavController) {

    val context = LocalContext.current

    LazyColumn {
        items(items = goalsViewModel.goals ){ goal ->
            if (goal != null) {
                goal.Name?.let {
                    goal.Date?.let { it1 ->
                        GoalsDisplay(goal = goal) {
                            CustomIcon(
                                icon = Icons.Outlined.Edit,
                                description = "Edit Goal",
                                clickEvent = { navController.navigate(Screen.UpdateGoalScreen.route) })

                            CustomIcon(
                                icon = Icons.Default.Delete,
                                description = "Delete Goal",
                                clickEvent = { deleteDataFromFirebase(courseID = goal.getId(), context = context) })
                        }
                    }
                }
            }
        }
    }
}



private fun deleteDataFromFirebase(courseID: String?, context: Context) {

    val db = FirebaseFirestore.getInstance();

    db.collection("Goals").document(courseID.toString()).delete().addOnSuccessListener {
        Toast.makeText(context, "Goal was deleted successfully..", Toast.LENGTH_SHORT).show()
    }.addOnFailureListener {
        Toast.makeText(context, "Fail to delete goal..", Toast.LENGTH_SHORT).show()
    }

}

private fun updateDataToFirebase(
    id: String?,
    date: String?,
    description: String?,
    name: String?,
    time: String?,
    context: Context
) {
    val updateGoal = Goal(Id = id, Date = date, Name = name, Description = description, Time = time)

    val db = FirebaseFirestore.getInstance();
    db.collection("Goals").document(id.toString()).set(updateGoal)
        .addOnSuccessListener {
            Toast.makeText(context, "Goal is updated successfully..", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener {
            Toast.makeText(context, "Fail to update goal : " + it.message, Toast.LENGTH_SHORT)
                .show()
        }
}


