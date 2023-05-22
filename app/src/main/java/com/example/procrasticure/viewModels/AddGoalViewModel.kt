package com.example.procrasticure.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.procrasticure.data.model.Goal
import com.example.procrasticure.data.repository.GoalRepository

// brauchen wir vllt dann wenn wir die Goals aus der DB auf den Screen bringen wollen
class AddGoalViewModel(
    val repository: GoalRepository
) : ViewModel() {

    private val _goals = MutableLiveData<List<Goal>>()
    val goal: LiveData<List<Goal>>
            get() = _goals
    fun getGoals(){
        _goals.value = repository.getGoals()
    }




}