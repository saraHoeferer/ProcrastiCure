package com.example.procrasticure.screens

const val DETAIL_ARGUMENT_KEY = "goalId"
const val ARGUMENT_KEY_2 = "goalName"

sealed class Screen(val route: String) {
    object GoalsScreen : Screen("main")
    object ProfileScreen : Screen("profile")

    object SubGoalsScreen : Screen("detail/{$DETAIL_ARGUMENT_KEY}/{$ARGUMENT_KEY_2}") {
        fun withIdandName(id: String, name: String): String {
            return this.route.replace(oldValue = "{$DETAIL_ARGUMENT_KEY}", newValue = id).replace(oldValue = "{$ARGUMENT_KEY_2}", newValue = name)
        }

    }

    object TimerScreen : Screen("timer")

    object AddGoalScreen : Screen("addGoal")

    object AddSubGoalScreen : Screen("addSubGoal")

    object ManageGoalsScreen : Screen("manageGoals")

    object ManageSubGoalsScreen : Screen("manageSubGoals")

    object Login : Screen("Login")
}