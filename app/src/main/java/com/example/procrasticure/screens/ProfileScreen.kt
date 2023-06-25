package com.example.procrasticure.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.procrasticure.R
import com.example.procrasticure.viewModels.BigViewModel
import com.example.procrasticure.viewModels.UserViewModel
import com.example.procrasticure.widgets.TopMenu
import kotlinx.coroutines.launch

// show user profile
@Composable
fun ProfileScreen(navController: NavController, sessionViewModel: BigViewModel, userViewModel: UserViewModel){
    Column {
        TopMenu(arrowBackClicked = { navController.popBackStack() },
            heading = "My Profile")
        ProfileDetails(sessionViewModel = sessionViewModel, userViewModel = userViewModel, navController)
    }
}

@Composable
fun ProfileDetails(sessionViewModel: BigViewModel, userViewModel: UserViewModel, navController: NavController) {
    val context = LocalContext.current
    Card(modifier = Modifier) {

        var emailshow by remember {
            mutableStateOf(false)
        }
        var email by remember { mutableStateOf("")}
        var passwordShow by remember {
            mutableStateOf(false)
        }
        var password by remember { mutableStateOf("")}
        val coroutineScope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier) {
                Image(
                    modifier = Modifier.clip(CircleShape),
                    painter = painterResource(id = R.drawable.profilepicture),
                    contentDescription = "Profile"
                )
            }

            Spacer(modifier = Modifier.size(25.dp))

            sessionViewModel.user.getFirebaseUser()?.email?.let {
                Text(
                    text = it,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(text = "points: ${sessionViewModel.user.getPoints()}", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

            Spacer(modifier = Modifier.size(80.dp))

            Button(onClick = {
                if (!emailshow) {
                    emailshow = true
                } else {
                    if (email.isNotEmpty()){
                        coroutineScope.launch {
                            userViewModel.editEmail(email, sessionViewModel, context)
                        }
                        navController.popBackStack()
                        navController.navigate(Screen.GoalsScreen.route)
                    } else {
                        emailshow = !emailshow
                    }
                }
            }) {
                Text(text = "Change e-mail", fontSize = 18.sp)
            }
            AnimatedVisibility(visible = emailshow) {
                email = simpleTextField2(label = "Change your email", modifier = Modifier)
            }
            Spacer(modifier = Modifier.size(25.dp))

            Button(onClick = { if (!passwordShow) {
                passwordShow = true
            } else {
                if (password.isNotEmpty()){
                    coroutineScope.launch { userViewModel.editPassword(password, sessionViewModel, context) }
                    navController.popBackStack()
                    navController.navigate(Screen.GoalsScreen.route)
                } else {
                    passwordShow = !passwordShow
                }
            } }) {
                Text(text = "Change password", fontSize = 18.sp)
            }
            AnimatedVisibility(visible = passwordShow) {
                password = simpleTextField2(label = "Change your Password", modifier = Modifier, KeyboardType.Password, PasswordVisualTransformation())
            }

            Spacer(modifier = Modifier.size(100.dp))

            Button(onClick = {
                coroutineScope.launch { userViewModel.delete(sessionViewModel = sessionViewModel) }
                navController.navigate(Screen.Login.route)
            }) {
                Text(text = "Delete Account", fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun simpleTextField2(label: String, modifier: Modifier, keyboardoptions: KeyboardType = KeyboardType.Email, animation: VisualTransformation = VisualTransformation.None): String {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(label) },
        modifier = modifier.width(320.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardoptions
        ),
        visualTransformation = animation
    )
    return text
}


