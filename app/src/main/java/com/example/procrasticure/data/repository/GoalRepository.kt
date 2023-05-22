package com.example.procrasticure.data.repository

import com.example.procrasticure.data.model.Goal

// zum retrieven der goals später
interface GoalRepository {
    fun getGoals(): List<Goal>
}