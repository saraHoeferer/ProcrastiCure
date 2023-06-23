package com.example.procrasticure.data.model

data class SubGoal(
    private var Id: String? = "",
    var Date: String? = "",
    var Name: String? = "",
    var Description: String? = "",
    var Time: String? = "",
) {
    fun setId(id: String) {
        this.Id = id
    }
}
