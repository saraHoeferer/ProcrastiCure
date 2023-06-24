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
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.procrasticure.data.repository.GoalRepository
import com.example.procrasticure.data.repository.GoalRepositoryImpl
import com.example.procrasticure.R
import com.example.procrasticure.viewModels.BigViewModel
import com.example.procrasticure.viewModels.UserViewModel
import com.example.procrasticure.viewModels.GoalsViewModel
import com.example.procrasticure.widgets.TopHomeMenu
import com.example.procrasticure.widgets.CustomIcon

import com.example.procrasticure.widgets.GoalsDisplay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GoalsScreen(navController: NavController, userViewModel: UserViewModel, sessionViewModel: BigViewModel, goalsRepository: GoalRepositoryImpl) {
    val colorPrimary = Color(98, 0, 238)
    val colorDisabled = Color(87, 87, 87, 13)

    var displayState by remember {
        mutableStateOf(true)
    }

    var buttonColorCurrent by remember {
        mutableStateOf(colorPrimary)
    }

    var buttonColorFinished by remember {
        mutableStateOf(colorDisabled)
    }
    var showMenu by remember { mutableStateOf(false) }

    var goalsViewModel = GoalsViewModel(sessionViewModel, goalsRepository)

    Column {
        TopHomeMenu(
            navController = navController,
            userViewModel = userViewModel,
            sessionViewModel = sessionViewModel
        )
        Row {
            Button(
                onClick = {
                    displayState = true

                },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = buttonColorCurrent),
                modifier = Modifier
                    .padding(4.dp)
            ) {
                Text(text = "Current Goals")
            }
            Button(
                onClick = { displayState = false },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = buttonColorFinished),
                modifier = Modifier
                    .padding(4.dp)
            ) {
                Text(text = "Finished Goals")
            }
            Box(contentAlignment = Alignment.TopEnd) {


                /*DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                    DropdownMenuItem(onClick = { /*TODO: filter methode aufrufen von goalsviewmodel*/ }) {
                        Text(text = "Deadline ascending")
                    }
                    DropdownMenuItem(onClick = { /*TODO: filter methode aufrufen von goalsviewmodel*/ }) {
                        Text(text = "Deadline early to late")
                    }
                    DropdownMenuItem(onClick = { /*TODO: filter methode aufrufen von goalsviewmodel*/ }) {
                        Text(text = "Name: alphabetical")
                    }
                }

                 */
            }



            if (displayState) {
                Spacer(modifier = Modifier.weight(1f))
                CustomIcon(
                    icon = Icons.Default.Add,
                    description = "Add Goal",
                    color = MaterialTheme.colors.primary
                ) {
                    navController.navigate(Screen.AddGoalScreen.route)
                }
            }
        }
        val options = listOf("Alphabetically", "Deadline: Ascending", "Deadline: Descending")
        var selectedOptionText by remember { mutableStateOf(options[0]) }

        ExposedDropdownMenuBox(
            expanded = showMenu,
            modifier = Modifier.fillMaxWidth(),
            onExpandedChange = {
                showMenu = !showMenu
            }
        ) {
            TextField(
                readOnly = true,
                value = selectedOptionText,
                onValueChange = { },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Sort By") },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_filter_alt_24),
                        contentDescription = "filter menu",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .padding(horizontal = 13.dp, vertical = 12.dp)
                            .size(30.dp)
                            .clickable { showMenu = !showMenu })
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                expanded = showMenu,
                onDismissRequest = {
                    showMenu = false
                }
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            selectedOptionText = selectionOption
                            showMenu = false
                        }
                    ) {
                        Text(text = selectionOption)
                        if(selectionOption=="Alphabetically") {
                            goalsViewModel.sortGoalsAlphabetically()
                        } else if(selectionOption=="Deadline: Ascending"){
                            goalsViewModel.sortGoalsByDeadlineAscending()
                        } else if(selectionOption == "Deadline: Descending"){
                            goalsViewModel.sortGoalsByDeadlineDescending()
                        }
                    }
                }
            }
        }

        if (displayState) {

            GoalList(navController = navController, goalsViewModel = goalsViewModel)
            buttonColorFinished = colorDisabled
            buttonColorCurrent = colorPrimary

        } else {
            FinishedGoalList(goalsViewModel = goalsViewModel)
            buttonColorCurrent = colorDisabled
            buttonColorFinished = colorPrimary
        }
    }

}


@Composable
fun FinishedGoalList(goalsViewModel: GoalsViewModel) {
    val goalListState = remember {goalsViewModel.goals}
    LazyColumn {
        items(items = goalListState) { goal ->
            if (goal.Finished!!) {
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .clip(shape = RoundedCornerShape(18.dp))
                        .background(Color(87, 87, 87, 22))
                        .width(400.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 18.dp, vertical = 10.dp)
                    ) {
                        goal.Name?.let {
                            Text(
                                text = it,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                            )
                        }
                        Text(
                            text = "Points earned: 200",
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GoalList(
    navController: NavController,
    goalsViewModel: GoalsViewModel

) {
    val goalListState = remember { goalsViewModel.goals }


    LazyColumn {

        items(items = goalListState) { goal ->
            if (!goal.Finished!!) {
                goal.Name?.let {
                    GoalsDisplay(
                        goal= goal,
                        onClick = {
                            navController.navigate(
                                Screen.SubGoalsScreen.withIdandName(
                                    goal.Id!!,
                                    goal.Name!!,
                                    goal.Points!!.toString()
                                )
                            )
                        },
                        onLongClick = { navController.navigate(Screen.ManageGoalsScreen.route) }
                    ) {
                        CustomIcon(
                            icon = Icons.Default.KeyboardArrowRight,
                            description = "Go to Goal Details",
                            color = Color.Gray
                        ) {

                            navController.navigate(
                                Screen.SubGoalsScreen.withIdandName(
                                    goal.Id!!,
                                    goal.Name!!,
                                    goal.Points!!.toString()
                                )
                            )
                        }
                    }
                }
            }
        }
    }


}

