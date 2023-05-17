package com.example.procrasticure.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.procrasticure.screens.*

val goalList = listOf("1# Goal", "2# Goal", "3# Goal")
@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route){
        composable(route = Screen.MainScreen.route){
            HomeScreen(movieList = goalList, navController = navController)
        }

        composable(route = Screen.ProfileScreen.route){
            ProfileScreen(navController = navController)
        }

        composable(route = Screen.TimerScreen.route){
            Timer(navController = navController)
        }

        composable(route = Screen.AddGoalScreen.route){
            AddGoalScreen(navController = navController)
        }

        composable(route = Screen.AddSubGoalScreen.route){
            AddSubGoalScreen(navController = navController)
        }

        composable(route = Screen.ManageGoalsScreen.route){
            ManageGoalsScreen(navController = navController, movieList = goalList)
        }

        composable(route = Screen.ManageSubGoalsScreen.route){
            ManageSubgoalsScreen(navController = navController)
        }

        composable(
            Screen.DetailScreen.route,
            arguments = listOf(navArgument(name = DETAIL_ARGUMENT_KEY) {type = NavType.StringType})
        ) { backStackEntry ->    // backstack contains all information from navhost
                DetailScreen(
                    navController = navController,
                    goalId = backStackEntry.arguments?.getString(DETAIL_ARGUMENT_KEY)
                ) // get the argument from navhost that will be passed

        }
    }
}