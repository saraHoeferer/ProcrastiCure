package com.example.procrasticure.widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GoalsDisplay(
    goal: String,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    content: @Composable () -> Unit = {}){
    Box(
        modifier = Modifier
            .padding(5.dp)
            .clip(shape = RoundedCornerShape(18.dp))
            .background(Color(87, 87, 87, 22))
            .combinedClickable(onClick = { onClick() }, onLongClick = { onLongClick() })
    ) {
       GoalDisplay(goal = goal, content)
    }
}
@Composable
fun GoalDisplay(goal:String, content: @Composable ()-> Unit = {}){
    Row(
        modifier = Modifier
            .width(400.dp)
            .padding(5.dp)
    ) {
        Text(
            text = goal,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 12.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        content()
    }
}