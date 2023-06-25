package com.example.procrasticure.data.model

import com.google.firebase.database.Exclude
import org.checkerframework.checker.nullness.qual.NonNull

data class Goal(
    var Id: String? = "",
    var Date: String? = "",
    var Name: String? = "",
    var Description: String? = "",
    var Time: String? = "",
    var UserId: String? = "",
    var Finished: Boolean? = false,
    var Points: Long = 0,
    var WarningPreference: Long = 0

)
