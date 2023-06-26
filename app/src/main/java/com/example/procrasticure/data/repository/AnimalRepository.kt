package com.example.procrasticure.data.repository

import android.content.Context
import com.example.procrasticure.data.Animals
import com.example.procrasticure.viewModels.BigViewModel
import com.google.firebase.firestore.FirebaseFirestore

interface AnimalRepository {
    val database: FirebaseFirestore

    suspend fun buyAnimal(sessionViewModel: BigViewModel, animal: Animals, context: Context)

    suspend fun getUserAnimals(
        sessionViewModel: BigViewModel,
        animalList: ArrayList<Animals>
    ): ArrayList<Animals>

    suspend fun listenToChanges(
        sessionViewModel: BigViewModel,
        animalList: ArrayList<Animals>
    ): ArrayList<Animals>

    fun getAnimalsById(animalId: String, animalList: ArrayList<Animals>): Animals?
}