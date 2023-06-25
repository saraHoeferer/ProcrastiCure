package com.example.procrasticure.data.repository

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.procrasticure.data.model.Goal
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class SubGoalRepositoryImpl: SubGoalRepository {
    override val database = FirebaseFirestore.getInstance()

    override suspend fun getSubGoals(goalId: String, subGoalList: ArrayList<Goal>): ArrayList<Goal> {
        var docRef = database.collection("Goals/$goalId/SubGoals")
        docRef.get()
            .addOnSuccessListener { queryDocumentSnapshot ->
                if (!queryDocumentSnapshot.isEmpty) {
                    val list = queryDocumentSnapshot.documents
                    subGoalList.clear() // without: would expand list all the time
                    for (document in list) {
                        val subgoal: Goal? = document.toObject(Goal::class.java)
                        if (subgoal != null) {
                            subgoal.Id = document.id
                            subGoalList.add(subgoal)
                        }

                    }
                } else {
                    println("No Goals")
                }
            }
            .addOnFailureListener{
                println("Failure")
            }.await()
        return subGoalList
    }

    override suspend fun listenToChanges(
        goalId: String,
        subGoalList: ArrayList<Goal>
    ): ArrayList<Goal> {
        val docRef = database.collection("Goals/$goalId/SubGoals")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val documents = snapshot.documentChanges
                //Wenns wirklich nur changes sind - jetzt spielt die ganze subgoal liste einfach nochmal rein
                subGoalList.clear()
                for (change in documents) {
                    val goal: Goal = change.document.toObject(Goal::class.java)
                    goal.Id = change.document.id
                    subGoalList.add(goal)
                }

            }
        }
        return subGoalList
    }

    override suspend fun addSubGoal(goalId: String, subGoal: Goal, context: Context) {
        database.collection("Goals/$goalId/SubGoals")
            .add(subGoal)
            .addOnSuccessListener {
                Toast.makeText(context, "Record Inserted", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Failed to insert Record: $e", Toast.LENGTH_SHORT).show()
            }
    }

    override suspend fun checkSubGoal(goalId: String, subGoalId: String, goalPoints: Long): Long {
        val docRef = database.collection("Goals/${goalId}/SubGoals").document(subGoalId)
        docRef
            .update("finished",true)
            .addOnSuccessListener { println("Subgoal check updated") }
            .addOnFailureListener { println("Failure check subgoal") }

        val docRef2 = goalId.let { database.collection("Goals").document(it) }
        docRef2
            .update("points", goalPoints + 50)
            .addOnSuccessListener { println("Subgoal points updated") }
            .addOnFailureListener { println("Failure points subgoal") }
        return goalPoints + 50
    }

    override suspend fun uncheckSubGoal(goalId: String, subGoalId: String, goalPoints: Long): Long {
        val docRef = database.collection("Goals/${goalId}/SubGoals").document(subGoalId)
        docRef
            .update("finished",false)
            .addOnSuccessListener { println("Subgoal points updated") }
            .addOnFailureListener { println("Failure points subgoal") }

        val docRef2 = goalId.let { database.collection("Goals").document(it) }
        docRef2
            .update("points", goalPoints - 50)
            .addOnSuccessListener { println("Subgoal points updated") }
            .addOnFailureListener { println("Failure points subgoal") }
        return goalPoints - 50
    }
}