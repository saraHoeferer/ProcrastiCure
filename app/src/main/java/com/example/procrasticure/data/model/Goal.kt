package com.example.procrasticure.data.model

import com.google.firebase.database.Exclude
import org.checkerframework.checker.nullness.qual.NonNull

data class Goal(
    var Id: String? = "",
    var Date: String? = "",
    var Name: String? = "",
    var Description: String? = "",
    var Time: String? = "",
)
