package com.example.procrasticure.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.procrasticure.data.model.Goal

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GoalsDisplay(
    goal: Goal,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .clip(shape = RoundedCornerShape(18.dp))
            .background(Color(87, 87, 87, 22))
            .combinedClickable(onClick = { onClick() }, onLongClick = { onLongClick() })
    ) {
        GoalDisplay(goal = goal, content = content)
    }
}

@Composable
fun GoalDisplay(goal: Goal, content: @Composable () -> Unit = {}) {
    var expanded by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {

        Row {
            Text(
                text = goal.Name!!,
                fontSize = 20.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 12.dp)
            )
            Spacer(modifier = Modifier.weight(1f))

            content()
        }
        Divider(modifier = Modifier.fillMaxWidth())
        Row {


            Text(
                text = "Due: ${goal.Date!!}",
                fontSize = 18.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 12.dp)
            )
            IconButton(
                modifier = Modifier.padding(2.dp),
                onClick = { expanded = !expanded }) {
                Icon(
                    imageVector =
                    if (expanded) Icons.Filled.KeyboardArrowDown
                    else Icons.Filled.KeyboardArrowUp,
                    contentDescription = "expand",
                    modifier = Modifier
                        .size(20.dp),
                    tint = Color.DarkGray
                )
            }
        }
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn(),
            exit = fadeOut()
        ) {

            Column(modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)) {

                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 15.sp)) {
                        append("Until: ")
                        append(goal.Time!!)
                    }
                })

                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 15.sp)) {
                        append("Description: ")

                        append(goal.Description!!)

                    }
                })


            }

        }


    }
}