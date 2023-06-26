package com.example.procrasticure.data.repository

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.procrasticure.data.Animals
import com.example.procrasticure.viewModels.BigViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AnimalRepositoryImpl : AnimalRepository {
    override val database = FirebaseFirestore.getInstance()

    override suspend fun buyAnimal(
        sessionViewModel: BigViewModel,
        animal: Animals,
        context: Context
    ) {
        if (sessionViewModel.user.getPoints()!! >= animal.price!!) {
            database.collection("Users/${sessionViewModel.user.getId()}/Animals")
                .add(animal)
                .addOnSuccessListener {
                    Toast.makeText(context, "Record Inserted", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Failed to insert Record: $e", Toast.LENGTH_SHORT)
                        .show()
                }

            sessionViewModel.user.setPoints(sessionViewModel.user.getPoints()!! - animal.price!!)

            val user = hashMapOf(
                "points" to sessionViewModel.user.getPoints()
            )

            sessionViewModel.user.getId().let {
                if (it != null) {
                    database.collection("Users").document(it).set(user)
                        .addOnSuccessListener { println("points to user") }
                        .addOnFailureListener { println("failure points to user") }
                }
            }
        } else {
            Toast.makeText(
                context,
                "You don't have enough points to buy a ${animal.url}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override suspend fun getUserAnimals(
        sessionViewModel: BigViewModel,
        animalList: ArrayList<Animals>
    ): ArrayList<Animals> {
        println("User/${sessionViewModel.user.getId()}/Animals")
        database.collection("Users/${sessionViewModel.user.getId()}/Animals").get()
            .addOnSuccessListener { queryDocumentSnapshot ->
                if (!queryDocumentSnapshot.isEmpty) {
                    val list = queryDocumentSnapshot.documents
                    animalList.clear() // without: would expand list all the time
                    for (document in list) {
                        println(document)
                        val animal: Animals? = document.toObject(Animals::class.java)
                        if (animal != null) {
                            animal.Id = document.id
                            animalList.add(animal)
                        }

                    }
                } else {
                    println("No Animals")
                }
            }
            .addOnFailureListener {
                println("Failure")
            }.await()
        println(animalList.toString())
        return animalList
    }

    override suspend fun listenToChanges(
        sessionViewModel: BigViewModel,
        animalList: ArrayList<Animals>
    ): ArrayList<Animals> {
        println(animalList)
        val docRef = database.collection("Users/${sessionViewModel.user.getId()}/Animals")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val documents = snapshot.documentChanges

                for (change in documents) {
                    val animals: Animals = change.document.toObject(Animals::class.java)
                    animals.Id = change.document.id
                    val oldGoal = getAnimalsById(animals.Id!!, animalList)
                    if (oldGoal != null) {
                        animalList.remove(oldGoal)
                    }
                    animalList.add(animals)
                }
            }
        }
        return animalList
    }

    override fun getAnimalsById(animalId: String, animalList: ArrayList<Animals>): Animals? {
        for (animal in animalList) {
            if (animal.Id == animalId) {
                return animal
            }
        }
        return null
    }
}