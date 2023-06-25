package com.example.procrasticure.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RadioButtonGroup(
    options: List<String>,
    selectedIndex: Int,
    onSelectedIndexChanged: (Int) -> Unit
) {
    Column {
        options.forEachIndexed { index, option ->
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal=38.dp)) {
                RadioButton(
                    selected = selectedIndex == index,
                    onClick = { onSelectedIndexChanged(index) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colors.primary
                    ),
                    modifier = Modifier.padding(vertical = 0.dp)
                )
                Text(
                    text = option,
                    modifier = Modifier.padding(vertical = 12.dp)

                )
            }
        }
    }
}