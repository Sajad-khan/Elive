package com.tropat.elive.screens.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tropat.elive.R
import com.tropat.elive.navigation.EliveScreens
import com.tropat.elive.utils.AppIcon
import com.tropat.elive.utils.ShowProgressBar
import com.tropat.elive.utils.brush
import com.tropat.elive.utils.myTextFieldColors


@Composable
fun LoginScreenContent(navController: NavController, loginViewModel: LoginViewModel = viewModel()){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(brush = brush)){
        Scaffold(modifier = Modifier.background(Color.Transparent),
            containerColor = Color.Transparent) {
            MainContent(paddingValues = it, navController, loginViewModel)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainContent(
    paddingValues: PaddingValues,
    navController: NavController,
    loginViewModel: LoginViewModel
){
    var email by remember{mutableStateOf("")}
    var password by remember{mutableStateOf("")}
    var passwordVisible by remember {mutableStateOf(false)}
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    var firebaseLoading by remember {mutableStateOf(false)}
    if(firebaseLoading){
        ShowProgressBar()
    }
    else{
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(paddingValues)
            .padding(20.dp)
            .verticalScroll(rememberScrollState())) {
            AppIcon()
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Login to your account",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White)
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email",
                    tint = Color.White)},
                value = email,
                onValueChange = {email = it},
                shape = RoundedCornerShape(10.dp),
                placeholder = { Text(text = "Email")},
                label = { Text(text = "Email")},
                colors = myTextFieldColors(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                value = password,
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.password),
                    contentDescription = "password Icon",
                    tint = Color.White)},
                onValueChange = {password = it},
                shape = RoundedCornerShape(10.dp),
                placeholder = { Text(text = "Password")},
                label = { Text(text = "Password")},
                colors = myTextFieldColors(),
                maxLines = 1,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = {passwordVisible = !passwordVisible}){
                        Icon(painter = painterResource(
                            id = if(passwordVisible) R.drawable.visibility else R.drawable.visibility_off),
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(60.dp))
            Button(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
                onClick = {
                    if(email.isNotEmpty() && password.isNotEmpty()){
                        firebaseLoading = true
                        loginViewModel.loginUser(email,
                            password,
                            {
                                Toast.makeText(context, "Logged in Successfully!", Toast.LENGTH_SHORT)
                                    .show()
                                firebaseLoading = false
                                navController.popBackStack()
                                navController.navigate(EliveScreens.HomeScreen.name)
                            },
                            {
                                firebaseLoading = false
                                Toast.makeText(context, "Invalid email or password!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        )
                    }
                    else{
                        Toast.makeText(context, "Email and Password cannot be empty!", Toast.LENGTH_SHORT).show()
                    }
                },
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Login")
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End){
                Text(text = "Don't have an account? ", color = Color.White)
                Text(text = "Sign Up",
                    color = Color.White,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        navController.navigate(EliveScreens.SignUpScreen.name)
                    }
                )
            }
        }
    }
}
