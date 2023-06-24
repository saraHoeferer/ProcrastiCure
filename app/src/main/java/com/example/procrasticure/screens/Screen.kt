package com.example.procrasticure.screens

const val DETAIL_ARGUMENT_KEY = "goalId"
const val ARGUMENT_KEY_2 = "goalName"

const val DESCRIPTION = "description"
const val DATE = "date"
const val TIME= "time"

const val POINTS = "points"

sealed class Screen(val route: String) {
    object GoalsScreen : Screen("main")
    object ProfileScreen : Screen("profile")

    object SubGoalsScreen : Screen("detail/{$DETAIL_ARGUMENT_KEY}/{$ARGUMENT_KEY_2}/{$POINTS}") {
        fun withIdandName(id: String, name: String, points: String): String {
            return this.route
                .replace(oldValue = "{$DETAIL_ARGUMENT_KEY}", newValue = id)
                .replace(oldValue = "{$ARGUMENT_KEY_2}", newValue = name)
                .replace(oldValue = "{$POINTS}", newValue = points)
        }

    }

    object TimerScreen : Screen("timer")

    object UpdateGoalScreen : Screen("updateGoalScreen/{$ARGUMENT_KEY_2}/{$DESCRIPTION}/{$DATE}/{$TIME}"){
        fun withDetails(name: String, description: String, date: String, time: String): String {
            return this.route
                .replace(oldValue =  "{$ARGUMENT_KEY_2}", newValue = name)
                .replace(oldValue = "{$DESCRIPTION}", newValue = description)
                .replace(oldValue = "{$DATE}", newValue = date)
                .replace(oldValue = "{$TIME}", newValue = time)
        }
    }

    object AddGoalScreen : Screen("addGoal")

    object AddSubGoalScreen : Screen("addSubGoal/{$DETAIL_ARGUMENT_KEY}") {
        fun withGoalId(id: String): String{
            return this.route.replace(oldValue = "{$DETAIL_ARGUMENT_KEY}", newValue = id)
        }
    }

    object ManageGoalsScreen : Screen("manageGoals")

    object ManageSubGoalsScreen : Screen("manageSubGoals/{$DETAIL_ARGUMENT_KEY}"){
        fun withGoalId(id: String): String{
            return this.route.replace(oldValue = "{$DETAIL_ARGUMENT_KEY}", newValue = id)
        }
    }

    object Login : Screen("Login")

    object AnimalScreen : Screen("AnimalScreen")

    object AnimalShopScreen : Screen("AnimalShopScreen")
}