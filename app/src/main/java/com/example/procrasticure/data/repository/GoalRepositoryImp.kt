package com.example.procrasticure.data.repository

import com.example.procrasticure.data.model.Goal

// auch fürs retrieven der goals
class GoalRepositoryImp: GoalRepository {
    override fun getGoals(): List<Goal> {
        return arrayListOf()
    }

}