package com.shubhu1026.mapd721_a1_shubhampatel

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shubhu1026.mapd721_a1_shubhampatel.ui.theme.MAPD721A1ShubhamPatelTheme
import com.shubhu1026.mapd721_a1_shubhampatel.ui.theme.blue
import com.shubhu1026.mapd721_a1_shubhampatel.ui.theme.darkGreen
import com.shubhu1026.mapd721_a1_shubhampatel.ui.theme.darkRed
import com.shubhu1026.mapd721_a1_shubhampatel.ui.theme.darkYellow
import com.shubhu1026.mapd721_a1_shubhampatel.ui.theme.lightGreen
import com.shubhu1026.mapd721_a1_shubhampatel.ui.theme.lightRed
import com.shubhu1026.mapd721_a1_shubhampatel.ui.theme.lightYellow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MAPD721A1ShubhamPatelTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserScreen()
                }
            }
        }
    }
}

@Composable
fun UserScreen() {
    // context
    val context = LocalContext.current
    // scope
    val scope = rememberCoroutineScope()

    val dataStore = DataStoreManager(context)

    val savedUsernameState = dataStore.getUsername.collectAsState(initial = "")
    val savedEmailState = dataStore.getEmail.collectAsState(initial = "")
    val savedIdState = dataStore.getId.collectAsState(initial = "")

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Name Text Field
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Username", color = Color.Gray, fontSize = 14.sp) }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Email Text Field
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "User Email", color = Color.Gray, fontSize = 14.sp) },
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Id Text Field
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = id,
            onValueChange = { id = it },
            label = { Text(text = "User ID", color = Color.Gray, fontSize = 14.sp) },
        )
        Spacer(modifier = Modifier.height(16.dp))

        // row for the buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(modifier = Modifier
                .weight(1f)
                .padding(horizontal = 5.dp)
                .background(
                    color = lightYellow,
                    shape = RoundedCornerShape(10.dp)
                )
                .border(
                    width = 2.dp,
                    color = darkYellow,
                    shape = RoundedCornerShape(10.dp)
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black),
                onClick = {
                    // only store the data if all the text fields are filled
                    if(username != "" && email != "" && id != "") {
                        scope.launch {
                            dataStore.saveUserData(UserData(username, email, id.toInt()))
                        }
                        Toast.makeText(context, "User data saved.", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        Toast.makeText(context, "Input fields need to be filled.", Toast.LENGTH_SHORT).show()
                    }
                }) {
                Text("Save", )
            }

            Button(modifier = Modifier
                .weight(1f)
                .padding(horizontal = 5.dp)
                .background(
                    color = lightGreen,
                    shape = RoundedCornerShape(10.dp)
                )
                .border(
                    width = 2.dp,
                    color = darkGreen,
                    shape = RoundedCornerShape(10.dp)
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black),
                onClick = {
                    // Clear button functionality
                    scope.launch {
                        dataStore.clearUserData()
                    }
                    // Reset input fields after clearing
                    username = ""
                    email = ""
                    id = ""

                    Toast.makeText(context, "User data cleared.", Toast.LENGTH_SHORT).show()
                }) {
                Text("Clear")
            }

            Button(modifier = Modifier
                .weight(1f)
                .padding(horizontal = 5.dp)
                .background(
                    color = lightRed,
                    shape = RoundedCornerShape(10.dp)
                )
                .border(
                    width = 2.dp, // Border width
                    color = darkRed, // Border color
                    shape = RoundedCornerShape(10.dp)
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black),
                onClick = {
                    username = savedUsernameState.value ?: ""
                    email = savedEmailState.value ?: ""
                    id = if(savedIdState.value != -1) {
                        (savedIdState.value ?: "").toString()
                    } else {
                        ""
                    }

                    if(username == "" && email == "" && id == "")
                    {
                        Toast.makeText(context, "User data not found.", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        Toast.makeText(context, "User data loaded.", Toast.LENGTH_SHORT).show()
                    }
                }) {
                Text("Load")
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    border = BorderStroke(width = 1.dp, color = Color(0, 0, 0)),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(20.dp),
        ) {
            Text("Shubham Patel", color = blue)
            Text("301366205", color = blue)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserScreenPreview() {
    MAPD721A1ShubhamPatelTheme {
        UserScreen()
    }
}