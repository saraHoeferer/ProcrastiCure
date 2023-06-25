package com.example.procrasticure.data.model

data class Goal(
    var Id: String? = "",
    var Date: String? = "",
    var Name: String? = "",
    var Description: String? = "",
    var Time: String? = "",
    var UserId: String? = "",
    var Finished: Boolean? = false,
    var Points: Long = 0,
    var WarningPreference: Int = 0

)
