package com.example.procrasticure.screens

import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.procrasticure.data.repository.SubGoalRepository
import com.example.procrasticure.data.repository.SubGoalRepositoryImpl
import com.example.procrasticure.viewModels.BigViewModel
import com.example.procrasticure.viewModels.GoalsViewModel
import com.example.procrasticure.viewModels.SubGoalsViewModel
import com.example.procrasticure.widgets.CustomIcon
import com.example.procrasticure.widgets.SubGoalsDisplay
import com.example.procrasticure.widgets.TopMenu
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ManageSubGoalsScreen(navController: NavController, goalID: String?, goalRepository: SubGoalRepositoryImpl) {
    val subGoalsViewModel: SubGoalsViewModel = goalID?.let { SubGoalsViewModel(it, goalRepository) }!!

    Column {
        TopMenu(heading = "Editing Subgoals", arrowBackClicked = { navController.popBackStack() })
        EditingSubgoalsDisplay(subGoalsViewModel = subGoalsViewModel, goalID= goalID, navController = navController)
    }
}

@Composable
fun EditingSubgoalsDisplay(subGoalsViewModel: SubGoalsViewModel, goalID: String, navController: NavController) {
    val goalListState = remember { subGoalsViewModel.subGoals }
    LazyColumn() {
        items(items = goalListState) { subgoal ->
            SubGoalsDisplay(subgoal = subgoal){
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

                            navController.navigate(Screen.UpdateSubGoalScreen
                            .withDetails(
                                goalID = goalID,
                                name = subgoal.Name!!,
                                description = subgoal.Description!!,
                                date= subgoal.Date!!,
                                time = subgoal.Time!!,
                                subGoalID = subgoal.Id!!)) }
                    )
                    CustomIcon(
                        icon = Icons.Default.Delete,
                        description = "Delete a specific SubGoal",
                        size = 30,
                        clickEvent = { deleteOneSubGoals(goalID = goalID, SubGoalID = subgoal.Id)}
                    )
                }
            }
        }
    }
}

fun deleteSubGoals(courseID: String?){

    val db = FirebaseFirestore.getInstance()

    db.collection("Goals")
        .document(courseID.toString())
        .collection("SubGoals")
        .get()
        .addOnSuccessListener { docs ->
            for (document in docs){
                Log.d("SUBGOAL ID", document.id)
                deleteOneSubGoals(SubGoalID = document.id, goalID = courseID)
            }
        }
        .addOnFailureListener{ e -> Log.d(ContentValues.TAG, "Error getting documents: ", e)}

}

fun deleteOneSubGoals(SubGoalID: String?, goalID: String?){

    val db = FirebaseFirestore.getInstance()
    db.collection("Goals")
        .document(goalID.toString())
        .collection("SubGoals")
        .document(SubGoalID.toString())
        .delete()
        .addOnSuccessListener { Log.d("TAG", "SubGoal was deleted successfully!") }
        .addOnFailureListener { e -> Log.d(ContentValues.TAG, "Error deleting subgoals: ", e) }
}