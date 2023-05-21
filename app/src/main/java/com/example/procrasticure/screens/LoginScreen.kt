package com.example.procrasticure.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun Login(navController: NavController){

    Column() {
        LoginDetails(navController)
    }

}


@Composable
fun LoginDetails(navController: NavController){

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
