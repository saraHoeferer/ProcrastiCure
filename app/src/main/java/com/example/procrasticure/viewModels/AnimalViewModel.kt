package com.example.procrasticure.viewModels

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procrasticure.data.Animals
import com.example.procrasticure.data.repository.AnimalRepositoryImpl
import kotlinx.coroutines.launch
import javax.inject.Inject

class AnimalViewModel  @Inject constructor(private val sessionViewModel: BigViewModel, private val animalRepositoryImpl: AnimalRepositoryImpl): ViewModel() {
    private var _animals : MutableLiveData<ArrayList<Animals>> = MutableLiveData<ArrayList<Animals>>()

    val animals: ArrayList<Animals> = ArrayList()

    init {
        Log.d(ContentValues.TAG, "init is started")
        viewModelScope.launch {
            if (sessionViewModel.user.getId() != "") {
                getUserAnimals(sessionViewModel)
                listenToChanges(sessionViewModel)
            }
        }
    }

    suspend fun getUserAnimals(sessionViewModel: BigViewModel){
        _animals.value = animalRepositoryImpl.getUserAnimals(sessionViewModel = sessionViewModel, animalList = animals)
    }

    suspend fun listenToChanges(sessionViewModel: BigViewModel){
        _animals.value = animalRepositoryImpl.listenToChanges(sessionViewModel = sessionViewModel, animalList = animals)
    }

    suspend fun buyAnimal(sessionViewModel: BigViewModel, animals: Animals, context: Context){
        animalRepositoryImpl.buyAnimal(sessionViewModel, animals, context)
    }
}