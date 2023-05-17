package com.example.procrasticure.screens

const val DETAIL_ARGUMENT_KEY = "goalId"

sealed class Screen(val route: String) {
    object MainScreen : Screen("main")
    object ProfileScreen : Screen("profile")

    object DetailScreen : Screen("detail/{$DETAIL_ARGUMENT_KEY}") {
        fun withId(id: String): String {
            return this.route.replace(oldValue = "{$DETAIL_ARGUMENT_KEY}", newValue = id)
        }
    }

    object TimerScreen : Screen("timer")

    object AddGoalScreen : Screen("addGoal")

    object AddSubGoalScreen : Screen("addSubGoal")

    object ManageGoalsScreen : Screen("manageGoals")

    object ManageSubGoalsScreen : Screen("manageSubGoals")
}