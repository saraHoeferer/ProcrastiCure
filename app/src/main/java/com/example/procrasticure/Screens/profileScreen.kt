package com.example.procrasticure.Screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.procrasticure.R
import com.example.procrasticure.Widgets.TopMenu

// show user profile
@Composable
fun ProfileScreen(navController: NavController){
    Column() {
        TopMenu(navController = navController, arrowBackClicked = { navController.popBackStack() })
        ProfileDetails()
    }
}

@Composable
fun ProfileDetails(){
    Card(modifier = Modifier) {
        val mContext = LocalContext.current

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

                OutlinedButton(
                    onClick = {
                        Toast.makeText(
                            mContext,
                            "This is a Circular Button with a + Icon",
                            Toast.LENGTH_LONG
                        ).show()
                    },
                    modifier = Modifier.size(70.dp).align(Alignment.BottomEnd),
                    shape = CircleShape,
                    border = BorderStroke(4.dp, Color(0XFF0F9D58)),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Blue)
                ) {
                    // Adding an Icon "Add" inside the Button
                    Icon(modifier = Modifier
                        .size(30.dp),
                        imageVector = Icons.Default.Edit,
                        contentDescription = "edit profile picture",
                        tint = Color(0XFF0F9D58),

                    )
                }
            }

                Spacer(modifier = Modifier.size(25.dp))

                Text(text = "USERNAME", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                Text(text = "points: xxx", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

                Spacer(modifier = Modifier.size(80.dp))

            Button(onClick = { /*TODO*/ }) {
                    Text(text = "Change e-mail", fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.size(25.dp))

                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Change password", fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.size(100.dp))

                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Delete Account", fontSize = 18.sp)
                }
            }
        }
}

