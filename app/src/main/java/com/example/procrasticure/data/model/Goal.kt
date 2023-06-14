package com.example.procrasticure.data.model

import com.google.firebase.database.Exclude
import org.checkerframework.checker.nullness.qual.NonNull

data class Goal(
    private var Id: String? = "",
    var Date: String? = "",
    var Name: String? = "",
    var Description: String? = "",
    var Time: String? = "",
    var UserId: String? = ""

){
    fun getId(): String? {
        return this.Id
    }

    fun setId(id: String){
        this.Id = id
    }
}
