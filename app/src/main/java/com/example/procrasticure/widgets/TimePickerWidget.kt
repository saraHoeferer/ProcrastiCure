package com.example.procrasticure.widgets

import android.app.TimePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.procrasticure.R
import java.util.*

@Composable
fun TimePickerWidget(timeText: String): String {
    var timeText by remember {
        mutableStateOf(timeText)
    }
    // Fetching local context
    val mContext = LocalContext.current

    // Declaring and initializing a calendar
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    // Value for storing time as a string

    // Creating a TimePicker dialog
    val mTimePickerDialog = TimePickerDialog(
        mContext,
        { _, mHour: Int, mMinute: Int ->
            timeText = "$mHour:$mMinute"
        }, mHour, mMinute, false
    )

    Box {

        // On button click, TimePicker is
        // displayed, user can select a time
        OutlinedButton(modifier = Modifier
            .width(320.dp)
            .height(55.dp)
            .border(
                BorderStroke(1.dp, SolidColor(Color.Gray)),
                shape = RoundedCornerShape(3.dp)
            ),
            onClick = {
                mTimePickerDialog.show()
            }) {
            Text(
                text = timeText,
                modifier = Modifier.width(280.dp),
                fontSize = 15.sp,
                fontWeight = FontWeight.W400,
                color = Color.DarkGray,
                textAlign = TextAlign.Left
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.baseline_access_time_24),
            contentDescription = "Change Date",
            modifier = Modifier
                .padding(8.dp)
                .align(
                    Alignment.BottomEnd
                )
                .width(40.dp)
                .height(40.dp),
            tint = Color.DarkGray
        )
    }
    return timeText
}