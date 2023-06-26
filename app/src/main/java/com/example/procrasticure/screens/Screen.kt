package com.example.procrasticure.screens

const val GOAL_ID = "goalID"
const val GOAL_NAME = "goalName"

const val DESCRIPTION = "description"
const val DATE = "date"
const val TIME= "time"
const val SUBGOAL_ID= "SubGoalID"

const val POINTS = "points"

sealed class Screen(val route: String) {
    object GoalsScreen : Screen("main")
    object ProfileScreen : Screen("profile")

    object SubGoalsScreen : Screen("detail/{$GOAL_ID}/{$GOAL_NAME}/{$POINTS}") {
        fun withIdandName(id: String, name: String, points: String): String {
            return this.route
                .replace(oldValue = "{$GOAL_ID}", newValue = id)
                .replace(oldValue = "{$GOAL_NAME}", newValue = name)
                .replace(oldValue = "{$POINTS}", newValue = points)
        }

    }

    object TimerScreen : Screen("timer")

    object UpdateGoalScreen : Screen("updateGoalScreen/?goalID={$GOAL_ID}/?goalName={$GOAL_NAME}/?description={$DESCRIPTION}/?date={$DATE}/?time={$TIME}"){
        fun withDetails(goalID: String, name: String, description: String, date: String, time: String): String {
            return this.route
                .replace(oldValue =  "{$GOAL_ID}", newValue = goalID)
                .replace(oldValue =  "{$GOAL_NAME}", newValue = name)
                .replace(oldValue = "{$DESCRIPTION}", newValue = description)
                .replace(oldValue = "{$DATE}", newValue = date)
                .replace(oldValue = "{$TIME}", newValue = time)
        }
    }

    object UpdateSubGoalScreen : Screen("UpdateSubGoalScreen/?goalID={$GOAL_ID}/?goalName={$GOAL_NAME}/?description={$DESCRIPTION}/?date={$DATE}/?time={$TIME}/?subGoalID={$SUBGOAL_ID}"){
        fun withDetails(goalID: String, name: String, description: String, date: String, time: String, subGoalID: String): String {
            return this.route
                .replace(oldValue =  "{$GOAL_ID}", newValue = goalID)
                .replace(oldValue =  "{$GOAL_NAME}", newValue = name)
                .replace(oldValue = "{$DESCRIPTION}", newValue = description)
                .replace(oldValue = "{$DATE}", newValue = date)
                .replace(oldValue = "{$TIME}", newValue = time)
                .replace(oldValue = "{$SUBGOAL_ID}", newValue = subGoalID)
        }
    }

    object AddGoalScreen : Screen("addGoal")

    object AddSubGoalScreen : Screen("addSubGoal/{$GOAL_ID}") {
        fun withGoalId(id: String): String{
            return this.route.replace(oldValue = "{$GOAL_ID}", newValue = id)
        }
    }

    object ManageGoalsScreen : Screen("manageGoals")

    object ManageSubGoalsScreen : Screen("manageSubGoals/{$GOAL_ID}/{$POINTS}") {
        fun withGoalID(id : String, points: String): String{
            return this.route.replace(oldValue = "{$GOAL_ID}", newValue = id).replace(oldValue = "{$POINTS}", newValue = points)
        }
    }

    object Login : Screen("Login")

    object AnimalScreen : Screen("AnimalScreen")

    object AnimalShopScreen : Screen("AnimalShopScreen")
}