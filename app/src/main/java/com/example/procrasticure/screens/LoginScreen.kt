package com.example.procrasticure.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.procrasticure.viewModels.LoginViewModel
import com.example.procrasticure.widgets.InputFieldWithErrorLabel
import com.example.procrasticure.widgets.InputState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.checkerframework.checker.units.qual.UnitsMultiple
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun Login(navController: NavController, viewModel: LoginViewModel){

    Column() {
        LoginDetails(navController, viewModel)
    }

}


@Composable
fun LoginDetails(navController: NavController, viewModel: LoginViewModel){

    val uiState by viewModel.uiState.collectAsState()

    val localFocusManager = LocalFocusManager.current

    uiState.data?.let {
        navController.apply {
            popBackStack()
            navigate(Screen.MainScreen.route)
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

    Card() {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "ProcrastiCure", fontSize = 50.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.size(50.dp))

            AnimatedVisibility(visible = login) {
                Column() {
                    LoginName = SimpleTextFieldLogin("username", modifier = Modifier)
                    LoginPW = SimpleTextFieldLogin("password", modifier = Modifier)
                }
            }

            if (login){
                Spacer(modifier = Modifier.size(30.dp))
            }

            AnimatedVisibility(visible = !signup) {

                Button(onClick = {

                    if (LoginName.isNotEmpty() && LoginPW.isNotEmpty()){
                        navController.navigate(Screen.MainScreen.route)
                    }else{
                        login = !login
                    }
                                 }

                    , modifier = Modifier.size(150.dp, 50.dp)
                    , colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF5A27B3))) {
                    Text(text = "Login", color = Color.White)
                }
            }




            if (!signup){
                Spacer(modifier = Modifier.size(20.dp))
            }

            AnimatedVisibility(visible = signup) {
                Column() {
                    /*CreateAccountSection(
                        modifier = Modifier
                            .fillMaxWidth(),
                        focusManager = localFocusManager,
                        email = SignUpName,
                        onEmailChange = {
                            SignUpName = it
                            viewModel.resetUiState()
                        },
                        password = SignUpPW ,
                        onPasswordChange = {
                            SignUpPW = it
                            viewModel.resetUiState()
                        } ,
                        error = uiState.error
                    ) {
                        localFocusManager.clearFocus()
                        coroutineScope.launch { viewModel.signUp(SignUpName, SignUpPW) }
                    }*/
                    SignUpName = SimpleTextFieldLogin("username", modifier = Modifier)
                    SignUpPW = SimpleTextFieldLogin("password", modifier = Modifier)
                }
            }


            if (signup){
                Spacer(modifier = Modifier.size(30.dp))
            }

            AnimatedVisibility(visible = !login) {

                Button(onClick = {

                    if (SignUpName.isNotEmpty() && SignUpPW.isNotEmpty()){
                        coroutineScope.launch { viewModel.signUp(SignUpName, SignUpPW) }
                        navController.navigate(Screen.MainScreen.route)
                    }else{
                        signup = !signup
                    }

                                 }
                    , modifier = Modifier.size(150.dp, 50.dp)
                    , colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF5A27B3))) {
                    Text(text = "Sign up", color = Color.White)
                }
            }
        }
    }
}


@Composable
fun SimpleTextFieldLogin(label: String, modifier: Modifier): String {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(label) },
        modifier = modifier.width(320.dp)
    )

    return text
}

@Composable
fun CreateAccountSection(
    modifier: Modifier,
    focusManager: FocusManager,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    error: String?,
    onButtonClick:  () -> Unit
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
           text = "Create a new Account"
       )
        InputFieldWithErrorLabel(
            modifier = Modifier.fillMaxWidth(),
            input = email,
            onInputChange = {
                onEmailChange(it)
                emailValidityState = InputState.INITIAL
            },
            focusManager = focusManager,
            onUnfocusedState = {
                emailValidityState =
                    when (email.isNotEmpty()){
                        true -> InputState.VALID
                        else -> InputState.INVALID
                    }
            } ,
            inputState = emailValidityState,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done ,
            leadingIcon = Icons.Filled.Email,
            label = "Email"
        )
        InputFieldWithErrorLabel(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            input = password,
            onInputChange = {
                            onPasswordChange(it)
                passwordValidityState = InputState.INITIAL
            },
            focusManager =focusManager ,
            onUnfocusedState = {
                               passwordValidityState =
                                   if (password.isNotEmpty()) InputState.VALID else InputState.INVALID
            },
            inputState = passwordValidityState,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            leadingIcon = Icons.Filled.Lock,
            label = "Password"
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            onClick = { onButtonClick()    },
            enabled = isButtonEnabled
        ) {
            Text(modifier = Modifier.padding(6.dp),
                text = "Sign Up")
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
