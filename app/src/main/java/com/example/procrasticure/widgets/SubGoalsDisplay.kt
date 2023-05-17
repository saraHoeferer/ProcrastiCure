package com.example.procrasticure.widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SubGoalsDisplay(
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 3.dp)
            .clip(shape = RoundedCornerShape(18.dp))
            .background(Color(87, 87, 87, 22))
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { onLongClick() })
    ) {
        SubGoalDisplay {
            content()
        }
    }
}

@Composable
fun SubGoalDisplay(content: @Composable () -> Unit = {}) {
    Row(
        modifier = Modifier
            .width(400.dp)
            .padding(0.dp)
    ) {
        content()
    }
}