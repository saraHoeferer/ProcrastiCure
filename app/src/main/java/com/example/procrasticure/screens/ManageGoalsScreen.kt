package com.example.procrasticure.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.procrasticure.viewModels.GoalsViewModel
import com.example.procrasticure.widgets.TopMenu
import com.example.procrasticure.widgets.CustomIcon
import com.example.procrasticure.widgets.GoalsDisplay

@Composable
fun ManageGoalsScreen(navController: NavController) {
    val homeViewModel: GoalsViewModel = viewModel()

    Column {
        TopMenu(heading = "Editing Goals", arrowBackClicked = { navController.popBackStack() })
        EditingGoalsDisplay(homeViewModel)
    }
}

@Composable
fun EditingGoalsDisplay(homeViewModel: GoalsViewModel) {
    LazyColumn {
        items(items = homeViewModel.goals.value) { goal ->
            GoalsDisplay(goalName = goal.name) {
                CustomIcon(icon = Icons.Outlined.Edit, description = "Edit Goal")
                CustomIcon(icon = Icons.Default.Delete, description = "Delete Goal")
            }
        }
    }
}