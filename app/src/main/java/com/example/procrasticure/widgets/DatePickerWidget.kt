package com.example.procrasticure.widgets

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

// https://www.geeksforgeeks.org/date-picker-in-android-using-jetpack-compose/
@Composable
fun DatePickerWidget(dateText: String): String {

    var dateText by remember {
        mutableStateOf(dateText)
    }
    // Fetching the Local Context
    val mContext = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date(4,2,2)

    // Declaring a string value to
    // store date in string format
    //val mDate = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            dateText = "$mYear/${if (mMonth < 10){"0"+(mMonth + 1)}else{mMonth + 1}}/${if (mDayOfMonth < 10){"0"+(mDayOfMonth + 1)}else{mDayOfMonth + 1}}"
        }, mYear, mMonth, mDay
    )
    Box {


        // Creating a button that on click displays DatePickerDialog
        OutlinedButton(
            modifier = Modifier
                .width(320.dp)
                .height(55.dp)
                .border(
                    BorderStroke(1.dp, SolidColor(Color.Gray)),
                    shape = RoundedCornerShape(3.dp)
                )
                .background(color = Color.White),
            onClick = {
                mDatePickerDialog.show()

            }
        ) {
            Text(
                text = dateText,
                modifier = Modifier.width(280.dp),
                fontSize = 15.sp,
                fontWeight = FontWeight.W400,
                color = Color.DarkGray,
                textAlign = TextAlign.Left
            )

        }
        Icon(
            imageVector = Icons.Outlined.DateRange,
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
    return dateText
}