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
import com.example.procrasticure.data.repository.GoalRepositoryImpl
import com.example.procrasticure.data.repository.SubGoalRepositoryImpl
import com.example.procrasticure.data.repository.UserRepositoryImpl
import com.example.procrasticure.screens.*
import com.example.procrasticure.viewModels.BigViewModel
import com.example.procrasticure.viewModels.GoalsViewModel
import com.example.procrasticure.viewModels.SubGoalsViewModel
import com.example.procrasticure.viewModels.UserViewModel
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
    NavHost(navController = navController, startDestination = Screen.Login.route){
        composable(route = Screen.GoalsScreen.route){
            GoalsScreen(navController = navController,  sessionViewModel = sessionViewModel, userViewModel = userViewModel, goalsRepository = goalRepository, goalsViewModel = goalsViewModel)
        }

        composable(route = Screen.ProfileScreen.route){
            ProfileScreen(navController = navController, sessionViewModel = sessionViewModel, userViewModel= userViewModel)
        }

        composable(route = Screen.TimerScreen.route){
            Timer(navController = navController)
        }

        composable(route = Screen.AddGoalScreen.route){
            AddGoalScreen(navController = navController, sessionViewModel = sessionViewModel, goalsViewModel= GoalsViewModel(sessionViewModel, goalRepository))
        }

        composable(route = Screen.AddSubGoalScreen.route,
            arguments = listOf(navArgument(name = DETAIL_ARGUMENT_KEY) {type = NavType.StringType})
        ){ backStackEntry ->
                    AddSubGoalScreen(
                        navController = navController,
                        goalId = backStackEntry.arguments?.getString(DETAIL_ARGUMENT_KEY),
                        subGoalsViewModel = SubGoalsViewModel(backStackEntry.arguments?.getString(DETAIL_ARGUMENT_KEY)!!, subGoalRepository)
                    )
        }

        composable(route = Screen.ManageGoalsScreen.route){
            ManageGoalsScreen(navController = navController, sessionViewModel = sessionViewModel, goalsRepository = goalRepository )
        }

        composable(
            route = Screen.ManageSubGoalsScreen.route,
            arguments = listOf(navArgument(name = DETAIL_ARGUMENT_KEY) {type = NavType.StringType})
        ){backStackEntry ->
            ManageSubGoalsScreen(
                navController = navController,
                goalRepository = subGoalRepository,
                goalId = backStackEntry.arguments?.getString(DETAIL_ARGUMENT_KEY)
            )
        }

        composable(route = Screen.Login.route){
            println(sessionViewModel.user.getId())
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
            arguments = listOf(navArgument(name = DETAIL_ARGUMENT_KEY) {type = NavType.StringType}, navArgument(name= ARGUMENT_KEY_2) {type = NavType.StringType}, navArgument(name= POINTS) {type = NavType.StringType})
        ) { backStackEntry ->
                SubGoalsScreen(
                    navController = navController,
                    goalId = backStackEntry.arguments?.getString(DETAIL_ARGUMENT_KEY),
                    goalName = backStackEntry.arguments?.getString(ARGUMENT_KEY_2),
                    goalPoints = backStackEntry.arguments?.getString(POINTS),
                    goalsViewModel = GoalsViewModel(sessionViewModel, goalRepository),
                    sessionViewModel = sessionViewModel,
                    subGoalRepository = subGoalRepository
                )
        }

        composable(
            Screen.UpdateGoalScreen.route,
            arguments = listOf(
                navArgument(name = ARGUMENT_KEY_2) {type = NavType.StringType},
                navArgument(name= DESCRIPTION) {type = NavType.StringType},
                navArgument(name= DATE) {type = NavType.StringType},
                navArgument(name= TIME) {type = NavType.StringType},
            )
        ) { backStackEntry ->
            UpdateGoalScreen(
                navController = navController,
                name = backStackEntry.arguments?.getString(ARGUMENT_KEY_2),
                description = backStackEntry.arguments?.getString(DESCRIPTION),
                date = backStackEntry.arguments?.getString(DATE),
                time = backStackEntry.arguments?.getString(TIME),
            )

        }

        composable(route = Screen.AnimalShopScreen.route){
            AnimalShopScreen(navController = navController)
        }

        composable(route = Screen.AnimalScreen.route){
            AnimalScreen(navController = navController)
        }
    }
}