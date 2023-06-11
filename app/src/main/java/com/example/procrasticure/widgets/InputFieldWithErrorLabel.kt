package com.example.procrasticure.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputFieldWithErrorLabel(
    modifier: Modifier,
    input: String,
    onInputChange: (String) -> Unit,
    focusManager: FocusManager,
    onUnfocusedState: (FocusState) -> Unit,
    inputState: InputState,
    label: String,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    leadingIcon: ImageVector,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        modifier = modifier.onFocusChanged {
            if (!it.hasFocus && input.isNotEmpty()) onUnfocusedState(it)
        },
        value = input,
        onValueChange = {
            onInputChange(it)
        },
        isError = when(inputState){
            InputState.INITIAL, InputState.VALID -> false
            InputState.INVALID -> true
        },
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        ),
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = "some content"
            )
        },
        trailingIcon = {
            if (inputState == InputState.INVALID){
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Default.Edit,
                    contentDescription = "some Icon",
                    tint = MaterialTheme.colors.error
                )
            }
            if(inputState == InputState.INITIAL && input.isNotEmpty()) Icon(
               modifier = Modifier
                   .clickable {
                       onInputChange("")
                   }
                   .size(20.dp),
                imageVector = Icons.Default.Clear,
                contentDescription = "Some Icon"
            )
        }
    )
    if (inputState == InputState.INVALID) Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(start = 12.dp, top = 4.dp, bottom = 6.dp, end = 6.dp),
        text = "error",
        fontSize = 12.sp,
        color = MaterialTheme.colors.error
    )
}

enum class InputState{
    INITIAL, VALID, INVALID
}