package com.example.procrasticure.data.repository

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.procrasticure.data.model.Goal
import com.example.procrasticure.viewModels.BigViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class GoalRepositoryImpl(): GoalRepository {
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
            }
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
                database.collection("Users").document(it).set(user).addOnSuccessListener { println("points to user") }.addOnFailureListener { println("failure points to user") }
            }
        }
    }

    override suspend fun sortGoalsByName(
        sessionViewModel: BigViewModel,
        goalArrayList: ArrayList<Goal>
    ): ArrayList<Goal> {

        goalArrayList.sortBy { it.Name }
        print("GOAls:$goalArrayList")
      return goalArrayList
    }
}