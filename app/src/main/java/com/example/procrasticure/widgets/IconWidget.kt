package com.example.procrasticure.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CustomIcon(
    icon: ImageVector,
    description: String,
    color: Color = Color.Black,
    size: Int = 35,
    clickEvent: () -> Unit = {}
) {
    Icon(
        imageVector = icon,
        contentDescription = description,
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .width(size.dp)
            .height(size.dp)
            .clickable { clickEvent() },
        tint = color
    )
}