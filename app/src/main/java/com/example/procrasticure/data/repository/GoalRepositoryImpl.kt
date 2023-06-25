package com.example.procrasticure.data.repository

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.procrasticure.data.model.Goal
import com.example.procrasticure.viewModels.BigViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class GoalRepositoryImpl: GoalRepository {
    override val database = FirebaseFirestore.getInstance()

    override suspend fun getGoals(
        sessionViewModel: BigViewModel,
        goalArrayList: ArrayList<Goal>
    ): ArrayList<Goal> {
        database.collection("Goals").whereEqualTo("userId", "${sessionViewModel.user.getId()}").get()
            .addOnSuccessListener { queryDocumentSnapshot ->
                if (!queryDocumentSnapshot.isEmpty) {
                    val list = queryDocumentSnapshot.documents
                    goalArrayList.clear() // without: would expand list all the time
                    for (document in list) {
                        val goal: Goal? = document.toObject(Goal::class.java)
                        if (goal != null) {
                            goal.Id = document.id
                            goalArrayList.add(goal)
                        }

                    }
                } else {
                    println("No Goals")
                }
            }
            .addOnFailureListener{
                println("Failure")
            }.await()
        println(goalArrayList.toString())
        return goalArrayList
    }

    override suspend fun listenToChange(
        sessionViewModel: BigViewModel,
        goalArrayList: ArrayList<Goal>
    ): ArrayList<Goal> {
        println(goalArrayList)
        val docRef = database.collection("Goals").whereEqualTo("userId", "${sessionViewModel.user.getId()}")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val documents = snapshot.documentChanges

                for (change in documents) {
                    val goal: Goal = change.document.toObject(Goal::class.java)
                    goal.Id = change.document.id
                    val oldGoal = getGoalsById(goal.Id!!, goalArrayList)
                    if (oldGoal != null){
                        goalArrayList.remove(oldGoal)
                    }
                    goalArrayList.add(goal)
                }
            }
        }
        return goalArrayList
    }

    override fun getGoalsById(goalId: String, goalArrayList: ArrayList<Goal>): Goal? {
        for (goal in goalArrayList){
            if (goal.Id == goalId){
                return goal
            }
        }
        return null
    }

    override suspend fun addGoal(goal: Goal, context: Context) {
        database.collection("Goals")
            .add(goal)
            .addOnSuccessListener {
                Toast.makeText(context, "Record Inserted", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Failed to insert Record: $e", Toast.LENGTH_SHORT).show()
            }
    }

    override suspend fun finishGoal(goalId: String, goalPoints: Long, sessionViewModel: BigViewModel) {
        val docRef = database.collection("Goals").document(goalId)
        docRef
            .update("finished",true)
            .addOnSuccessListener { println("Goal check updated") }
            .addOnFailureListener { println("Failure check Goal") }
        sessionViewModel.user.setPoints(sessionViewModel.user.getPoints()!! +(200)+goalPoints)

        val user = hashMapOf(
            "points" to sessionViewModel.user.getPoints()
        )

        sessionViewModel.user.getId().let {
            if (it != null) {
                database.collection("Users").document(it).set(user).addOnSuccessListener { println("Updated User Points") }.addOnFailureListener { println("Failure when trying to update user Points") }
            }
        }
    }
    override suspend fun sortByCriteria(
        sessionViewModel: BigViewModel,
        goalArrayList: ArrayList<Goal>,
        criteria: String,
        order: Query.Direction
    ): ArrayList<Goal> {
        database.collection("Goals").orderBy(criteria, order)
            .get()
            .addOnSuccessListener { queryDocumentSnapshot ->
                if (!queryDocumentSnapshot.isEmpty) {
                    val list = queryDocumentSnapshot.documents
                    goalArrayList.clear() // without: would expand list all the time
                    for (document in list) {
                        val goal: Goal? = document.toObject(Goal::class.java)
                        if (goal != null) {
                            if (goal.UserId == sessionViewModel.user.getId()) {
                                goal.Id = document.id
                                goalArrayList.add(goal)
                            }
                        }

                    }
                } else {
                    println("Couldn't find goals while ordering by $criteria")
                }
            }
            .addOnFailureListener{
                println("Failure when trying to order by $criteria")
            }
            .await()
        return goalArrayList
    }

    override suspend fun deleteGoal(goalId: String, context: Context) {
        database.collection("Goals").document(goalId).delete().addOnSuccessListener {
            Toast.makeText(context, "Goal was deleted successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context, "Fail to delete goal", Toast.LENGTH_SHORT).show()
        }
    }

    override suspend fun modifyGoal(
        goalId: String,
        name: String,
        description: String,
        date: String,
        time: String,
        context: Context
    ) {
        database.collection("Goals")
            .document(goalId)
            .update("name", name, "description", description, "date", date, "time", time)
            .addOnSuccessListener {
                Toast.makeText(context, "Goal Updated", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Couldn't update Goal", Toast.LENGTH_SHORT).show()
            }
    }
}