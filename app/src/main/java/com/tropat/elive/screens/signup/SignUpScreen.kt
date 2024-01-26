package com.tropat.elive.screens.signup

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
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tropat.elive.navigation.EliveScreens
import com.tropat.elive.screens.login.LoginViewModel
import com.tropat.elive.utils.AppIcon
import com.tropat.elive.utils.ShowProgressBar
import com.tropat.elive.utils.brush
import com.tropat.elive.utils.isEmailValid
import com.tropat.elive.utils.isPasswordValid
import com.tropat.elive.utils.myTextFieldColors

@Composable
fun SignUpScreenContent(navController: NavController){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(brush = brush)){
        Scaffold(modifier = Modifier.background(Color.Transparent),
            containerColor = Color.Transparent,
        ) {
            MainContent(paddingValues = it, navController = navController)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainContent(paddingValues: PaddingValues, navController: NavController){
    val loginViewModel: LoginViewModel = viewModel()
    var email by remember{
        mutableStateOf("")
    }
    var password by remember{
        mutableStateOf("")
    }
    var firstName by remember{
        mutableStateOf("")
    }
    var lastName by remember{
        mutableStateOf("")
    }
    var isPasswordValid by remember{ mutableStateOf(true) }
    var isEmailValid by remember{ mutableStateOf(true) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    var firebaseLoading by remember { mutableStateOf(false) }

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
            Text(text = "Create an account",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White)
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                value = firstName,
                onValueChange = {firstName = it},
                shape = RoundedCornerShape(10.dp),
                placeholder = { Text(text = "First Name") },
                label = { Text(text = "First Name") },
                colors = myTextFieldColors(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {keyboardController?.hide()}
                ),
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                value = lastName,
                onValueChange = {lastName = it},
                shape = RoundedCornerShape(10.dp),
                placeholder = { Text(text = "Last Name") },
                label = { Text(text = "Last Name") },
                colors = myTextFieldColors(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = {email = it
                    isEmailValid = isEmailValid(email)
                },
                isError = !isEmailValid,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                shape = RoundedCornerShape(10.dp),
                placeholder = { Text(text = "Email") },
                label = { Text(text = "Email") },
                colors = myTextFieldColors(),
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = {password = it
                    isPasswordValid = isPasswordValid(password)
                },
                isError = !isPasswordValid,
                shape = RoundedCornerShape(10.dp),
                placeholder = { Text(text = "Password") },
                label = { Text(text = "Password") },
                colors = myTextFieldColors(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                maxLines = 1
            )
            if(!isPasswordValid){
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "Password should be at least 8 characters long and should contain letters, numbers and symbols",
                    color = MaterialTheme.colorScheme.error)
            }
            Spacer(modifier = Modifier.height(60.dp))
            Button(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
                onClick = {
                    if(firstName.isNotEmpty() && lastName.isNotEmpty()){
                        if(isEmailValid && isPasswordValid){
                            firebaseLoading = true
                            loginViewModel.createUser(email,
                                password,
                                {   firebaseLoading = false
                                    Toast.makeText(context, "User created successfully!", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                    navController.navigate(EliveScreens.HomeScreen.name)
                                },
                                {
                                    firebaseLoading = false
                                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                },
                                firstName,
                                lastName)
                        }
                        else if(isEmailValid && !isPasswordValid){
                            Toast.makeText(context, "Password is not valid", Toast.LENGTH_SHORT).show()
                        }
                        else if(isPasswordValid && !isEmailValid){
                            Toast.makeText(context, "Email is not valid", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(context, "Password and email are invalid", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Create Account")
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End){
                Text(text = "Already have an account? ", color = Color.White)
                Text(text = "Sign In",
                    color = Color.White,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        navController.navigate(EliveScreens.LoginScreen.name)
                    })
            }
        }
    }
}
