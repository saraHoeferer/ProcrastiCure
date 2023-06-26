package com.example.procrasticure.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.procrasticure.R

enum class InputState {
    INITIAL, VALID, INVALID
}

@Composable
fun InputFieldWithErrorLabelEmail(
    modifier: Modifier,
    input: String,
    onInputChange: (String) -> Unit,
    focusManager: FocusManager,
    onUnfocusedState: (FocusState) -> Unit,
    inputState: InputState,
    imeAction: ImeAction,
) {
    TextField(
        modifier = modifier.onFocusChanged {
            if (!it.hasFocus && input.isNotEmpty()) onUnfocusedState(it)
        },
        value = input,
        onValueChange = {
            onInputChange(it)
        },
        isError = when (inputState) {
            InputState.INITIAL, InputState.VALID -> false
            InputState.INVALID -> true
        },
        label = { Text("Email") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        visualTransformation = VisualTransformation.None,
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email icon"
            )
        },
        trailingIcon = {
            if (inputState == InputState.INITIAL && input.isNotEmpty()) Icon(
                modifier = Modifier
                    .clickable {
                        onInputChange("")
                    },
                imageVector = Icons.Default.Clear,
                contentDescription = "Reset Icon"
            )
        }
    )
}

@Composable
fun InputFieldWithErrorLabelPassword(
    modifier: Modifier,
    input: String,
    onInputChange: (String) -> Unit,
    focusManager: FocusManager,
    onUnfocusedState: (FocusState) -> Unit,
    inputState: InputState,
    imeAction: ImeAction
) {
    var showPassword by remember { mutableStateOf(value = false) }
    TextField(
        modifier = modifier.onFocusChanged {
            if (!it.hasFocus && input.isNotEmpty()) onUnfocusedState(it)
        },
        value = input,
        onValueChange = {
            onInputChange(it)
        },
        isError = when (inputState) {
            InputState.INITIAL, InputState.VALID -> false
            InputState.INVALID -> true
        },
        label = { Text("Password") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        visualTransformation = if (showPassword) {

            VisualTransformation.None

        } else {

            PasswordVisualTransformation()

        },
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Password Icon"
            )
        },
        trailingIcon = {
            Row {
                if (inputState == InputState.INITIAL && input.isNotEmpty()) Icon(
                    modifier = Modifier
                        .clickable {
                            onInputChange("")
                        }
                        .padding(top = 13.dp),
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Reset Icon"
                )
                if (showPassword) {
                    IconButton(onClick = { showPassword = false }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_visibility_24),
                            contentDescription = "hide_password"
                        )
                    }
                } else {
                    IconButton(
                        onClick = { showPassword = true }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_visibility_off_24),
                            contentDescription = "hide_password"
                        )
                    }
                }

            }

        }
    )
}

