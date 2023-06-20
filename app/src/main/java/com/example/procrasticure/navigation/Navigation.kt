package com.example.procrasticure.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.procrasticure.data.repository.UserRepositoryImpl
import com.example.procrasticure.screens.*
import com.example.procrasticure.viewModels.BigViewModel
import com.example.procrasticure.viewModels.UserViewModel

@Composable
fun Navigation(sessionViewModel: BigViewModel){
    val userRespository = UserRepositoryImpl()
    val userViewModel = UserViewModel(userRespository)
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Login.route){
        composable(route = Screen.GoalsScreen.route){
            GoalsScreen(navController = navController,  sessionViewModel = sessionViewModel, userViewModel = userViewModel)
        }

        composable(route = Screen.ProfileScreen.route){
            ProfileScreen(navController = navController, sessionViewModel = sessionViewModel, userViewModel= userViewModel)
        }

        composable(route = Screen.TimerScreen.route){
            Timer(navController = navController)
        }

        composable(route = Screen.AddGoalScreen.route){
            AddGoalScreen(navController = navController, sessionViewModel = sessionViewModel)
        }

        composable(route = Screen.AddSubGoalScreen.route){
            AddSubGoalScreen(navController = navController)
        }

        composable(route = Screen.ManageGoalsScreen.route){
            ManageGoalsScreen(navController = navController)
        }

        composable(route = Screen.ManageSubGoalsScreen.route){
            ManageSubGoalsScreen(navController = navController)
        }

        composable(route = Screen.Login.route){
            println(sessionViewModel.currentUserId)
            if (!sessionViewModel.isLoggedIn) {
                Login(
                    navController = navController,
                    userViewModel = userViewModel,
                    sessionViewModel = sessionViewModel
                )
            } else {
                GoalsScreen(navController = navController, userViewModel = userViewModel, sessionViewModel = sessionViewModel)
            }
        }

        composable(
            Screen.SubGoalsScreen.route,
            arguments = listOf(navArgument(name = DETAIL_ARGUMENT_KEY) {type = NavType.StringType}, navArgument(name= ARGUMENT_KEY_2) {type = NavType.StringType})
        ) { backStackEntry ->
                SubGoalsScreen(
                    navController = navController,
                    goalId = backStackEntry.arguments?.getString(DETAIL_ARGUMENT_KEY),
                    goalName = backStackEntry.arguments?.getString(ARGUMENT_KEY_2)
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