package com.example.procrasticure.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.procrasticure.data.model.Goal
import com.example.procrasticure.data.repository.*
import com.example.procrasticure.screens.*
import com.example.procrasticure.viewModels.*
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Navigation(sessionViewModel: BigViewModel){
    val userRespository = UserRepositoryImpl()
    val userViewModel = UserViewModel(userRespository)
    val goalRepository = GoalRepositoryImpl()
    val subGoalRepository = SubGoalRepositoryImpl()
    val navController = rememberNavController()
    val goalsViewModel = GoalsViewModel(sessionViewModel, goalRepository)
    val animalRepository = AnimalRepositoryImpl()
    val animalViewModel = AnimalViewModel(sessionViewModel, animalRepository)

    NavHost(navController = navController, startDestination = Screen.Login.route){
        composable(route = Screen.GoalsScreen.route){
            GoalsScreen(navController = navController,  sessionViewModel = sessionViewModel, userViewModel = userViewModel, goalsRepository = goalRepository, goalsViewModel = goalsViewModel)
        }

        composable(route = Screen.ProfileScreen.route){
            ProfileScreen(navController = navController, sessionViewModel = sessionViewModel, userViewModel= userViewModel)
        }

        composable(route = Screen.TimerScreen.route){
            Timer(navController = navController, userViewModel = userViewModel, sessionViewModel)
        }

        composable(route = Screen.AddGoalScreen.route){
            AddGoalScreen(navController = navController, sessionViewModel = sessionViewModel, goalsViewModel= GoalsViewModel(sessionViewModel, goalRepository))
        }

        composable(route = Screen.AddSubGoalScreen.route,
            arguments = listOf(navArgument(name = GOAL_ID) {type = NavType.StringType})
        ){ backStackEntry ->
                    AddSubGoalScreen(
                        navController = navController,
                        goalId = backStackEntry.arguments?.getString(GOAL_ID),
                        subGoalsViewModel = SubGoalsViewModel(backStackEntry.arguments?.getString(GOAL_ID)!!, subGoalRepository)
                    )
        }

        composable(route = Screen.ManageGoalsScreen.route){
            ManageGoalsScreen(navController = navController, sessionViewModel = sessionViewModel, goalsRepository = goalRepository )
        }

        composable(
            route = Screen.ManageSubGoalsScreen.route,
            arguments = listOf(navArgument(name = GOAL_ID) {type = NavType.StringType})
        ){backStackEntry ->
            ManageSubGoalsScreen(
                navController = navController,
                goalRepository = subGoalRepository,
                goalID = backStackEntry.arguments?.getString(GOAL_ID)
            )
        }

        composable(route = Screen.Login.route){
            if (!sessionViewModel.user.getLoggedIn()!!) {
                Login(
                    navController = navController,
                    userViewModel = userViewModel,
                    sessionViewModel = sessionViewModel
                )
            } else {
                GoalsScreen(navController = navController, userViewModel = userViewModel, sessionViewModel = sessionViewModel, goalRepository, goalsViewModel = goalsViewModel)
            }
        }

        composable(
            Screen.SubGoalsScreen.route,
            arguments = listOf(navArgument(name = GOAL_ID) {type = NavType.StringType}, navArgument(name= GOAL_NAME) {type = NavType.StringType}, navArgument(name= POINTS) {type = NavType.StringType})
        ) { backStackEntry ->
                SubGoalsScreen(
                    navController = navController,
                    goalId = backStackEntry.arguments?.getString(GOAL_ID),
                    goalName = backStackEntry.arguments?.getString(GOAL_NAME),
                    goalsViewModel = goalsViewModel,
                    sessionViewModel = sessionViewModel,
                    subGoalRepository = subGoalRepository,
                    goalPoints = backStackEntry.arguments?.getString(POINTS)
                )
        }

        composable(
            Screen.UpdateGoalScreen.route,
            arguments = listOf(
                navArgument("goalID") {type = NavType.StringType},
                navArgument("goalName") {type = NavType.StringType},
                navArgument("description") {type = NavType.StringType},
                navArgument("date") {type = NavType.StringType},
                navArgument("time") {type = NavType.StringType},
            )
        ) { backStackEntry ->
            UpdateGoalScreen(
                goalID = backStackEntry.arguments?.getString(GOAL_ID)!!,
                navController = navController,
                name = backStackEntry.arguments?.getString(GOAL_NAME),
                description = backStackEntry.arguments?.getString(DESCRIPTION),
                date = backStackEntry.arguments?.getString(DATE),
                time = backStackEntry.arguments?.getString(TIME)
            )

        }

        composable(
            Screen.UpdateSubGoalScreen.route,
            arguments = listOf(
                navArgument("goalID") {type = NavType.StringType},
                navArgument("goalName") {type = NavType.StringType},
                navArgument("description") {type = NavType.StringType},
                navArgument("date") {type = NavType.StringType},
                navArgument("time") {type = NavType.StringType},
                navArgument("SubGoalID") {type = NavType.StringType}
            )
        ) { backStackEntry ->
            UpdateSubGoalScreen(
                goalID = backStackEntry.arguments?.getString(GOAL_ID)!!,
                navController = navController,
                name = backStackEntry.arguments?.getString(GOAL_NAME),
                description = backStackEntry.arguments?.getString(DESCRIPTION),
                date = backStackEntry.arguments?.getString(DATE),
                time = backStackEntry.arguments?.getString(TIME),
                subGoalID = backStackEntry.arguments?.getString(SUBGOAL_ID)
            )

        }

        composable(route = Screen.AnimalShopScreen.route){
            AnimalShopScreen(navController = navController, animalViewModel = animalViewModel, sessionViewModel = sessionViewModel)
        }

        composable(route = Screen.AnimalScreen.route){
            AnimalScreen(navController = navController, animalViewModel = animalViewModel, sessionViewModel = sessionViewModel)
        }
    }
}