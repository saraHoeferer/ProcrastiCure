package com.example.procrasticure.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.procrasticure.viewModels.BigViewModel
import com.example.procrasticure.viewModels.UserViewModel
import com.example.procrasticure.widgets.InputFieldWithErrorLabelEmail
import com.example.procrasticure.widgets.InputFieldWithErrorLabelPassword
import com.example.procrasticure.widgets.InputState
import kotlinx.coroutines.launch


@Composable
fun Login(
    navController: NavController,
    userViewModel: UserViewModel,
    sessionViewModel: BigViewModel
) {

    Column {
        LoginDetails(navController, userViewModel, sessionViewModel)
    }

}


@Composable
fun LoginDetails(
    navController: NavController, userViewModel: UserViewModel, sessionViewModel: BigViewModel
) {

    val uiState by userViewModel.uiState.collectAsState()

    val localFocusManager = LocalFocusManager.current

    uiState.data?.let {
        navController.apply {
            popBackStack()
            navigate(Screen.GoalsScreen.route)
        }
    }

    var signup by remember {
        mutableStateOf(false)
    }

    var login by remember {
        mutableStateOf(false)
    }

    var LoginName by remember {
        mutableStateOf("")
    }

    var LoginPW by remember {
        mutableStateOf("")
    }

    var SignUpName by remember {
        mutableStateOf("")
    }

    var SignUpPW by remember {
        mutableStateOf("")
    }

    val coroutineScope = rememberCoroutineScope()
    val boxSize = with(LocalDensity.current) { 250.dp.toPx() }
    Card {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .height(250.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colors.primary,
                                MaterialTheme.colors.primarySurface,
                                MaterialTheme.colors.primaryVariant,

                                ),
                            start = Offset(0f, 0f), // top left corner
                            end = Offset(boxSize, boxSize) // bottom right corner
                        )
                    )
                    .fillMaxWidth()
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "ProcrastiCure",
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }



            Spacer(modifier = Modifier.size(50.dp))

            AnimatedVisibility(visible = login) {
                Column(modifier = Modifier.padding(20.dp)) {
                    CreateAccountSection(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "Log into your Account",
                        textButton = "Login",
                        focusManager = localFocusManager,
                        email = LoginName,
                        onEmailChange = {
                            LoginName = it
                            userViewModel.resetUiState()
                        },
                        password = LoginPW,
                        onPasswordChange = {
                            LoginPW = it
                            userViewModel.resetUiState()
                        },
                        error = uiState.error
                    ) {
                        localFocusManager.clearFocus()
                        coroutineScope.launch {
                            userViewModel.signIn(LoginName, LoginPW, sessionViewModel)
                        }
                    }
                    /*LoginName = SimpleTextFieldLogin("username", modifier = Modifier)
                    LoginPW = SimpleTextFieldLogin("password", modifier = Modifier)*/
                }
            }

            if (login) {
                Spacer(modifier = Modifier.size(30.dp))
            }

            AnimatedVisibility(visible = !signup) {

                Button(onClick = {

                    if (LoginName.isNotEmpty() && LoginPW.isNotEmpty()) {
                        coroutineScope.launch {
                            userViewModel.signIn(LoginName, LoginPW, sessionViewModel)
                        }
                        navController.navigate(Screen.GoalsScreen.route)
                    } else {
                        login = !login
                    }
                },
                    modifier = Modifier.size(150.dp, 50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF5A27B3))) {
                    if (login) {
                        Text(text = "Go back", color = Color.White)
                    } else {
                        Text(text = "Login", color = Color.White)
                    }
                }
            }




            if (!signup) {
                Spacer(modifier = Modifier.size(20.dp))
            }

            AnimatedVisibility(visible = signup) {
                Column(modifier = Modifier.padding(20.dp)) {
                    CreateAccountSection(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "Create a new Account",
                        textButton = "Sign Up",
                        focusManager = localFocusManager,
                        email = SignUpName,
                        onEmailChange = {
                            SignUpName = it
                            userViewModel.resetUiState()
                        },
                        password = SignUpPW,
                        onPasswordChange = {
                            SignUpPW = it
                            userViewModel.resetUiState()
                        },
                        error = uiState.error
                    ) {
                        localFocusManager.clearFocus()
                        coroutineScope.launch {
                            userViewModel.signUp(
                                SignUpName,
                                SignUpPW,
                                sessionViewModel
                            )
                        }
                    }
                }
            }


            if (signup) {
                Spacer(modifier = Modifier.size(30.dp))
            }

            AnimatedVisibility(visible = !login) {
                Button(
                    onClick = { signup = !signup },
                    modifier = Modifier.size(150.dp, 50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF5A27B3))
                ) {
                    if (signup) {
                        Text(text = "Go back", color = Color.White)
                    } else {
                        Text(text = "Sign up", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun CreateAccountSection(
    modifier: Modifier,
    text: String,
    textButton: String,
    focusManager: FocusManager,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    error: String?,
    onButtonClick: () -> Unit
) {
    var emailValidityState by rememberSaveable {
        mutableStateOf(InputState.INITIAL)
    }

    var passwordValidityState by rememberSaveable {
        mutableStateOf(InputState.INITIAL)
    }

    val isButtonEnabled = email.isNotEmpty() && password.isNotEmpty()

    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, bottom = 12.dp),
            text = text
        )
        InputFieldWithErrorLabelEmail(
            modifier = Modifier.fillMaxWidth(),
            input = email,
            onInputChange = {
                onEmailChange(it)
                emailValidityState = InputState.INITIAL
            },
            focusManager = focusManager,
            onUnfocusedState = {
                emailValidityState =
                    when (email.isNotEmpty()) {
                        true -> InputState.VALID
                        else -> InputState.INVALID
                    }
            },
            inputState = emailValidityState,
            imeAction = ImeAction.Done,
        )
        InputFieldWithErrorLabelPassword(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            input = password,
            onInputChange = {
                onPasswordChange(it)
                passwordValidityState = InputState.INITIAL
            },
            focusManager = focusManager,
            onUnfocusedState = {
                passwordValidityState =
                    if (password.isNotEmpty()) InputState.VALID else InputState.INVALID
            },
            inputState = passwordValidityState,
            imeAction = ImeAction.Done,
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            onClick = { onButtonClick() },
            enabled = isButtonEnabled
        ) {
            Text(
                modifier = Modifier.padding(6.dp),
                text = textButton
            )
        }

        if (!error.isNullOrEmpty()) Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .background(MaterialTheme.colors.error)
                .padding(6.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "error occurred",
                tint = Color.White,
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(25.dp)
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = error,
                fontSize = 14.sp,
                color = Color.White
            )
        }
    }
}
