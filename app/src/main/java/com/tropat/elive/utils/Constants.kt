package com.tropat.elive.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tropat.elive.R

val brush = Brush.verticalGradient(listOf(Color(0xFF397796), Color(0xFF9A4B4B)))
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun myTextFieldColors() = TextFieldDefaults.textFieldColors(
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedLabelColor = Color.White,
    unfocusedLabelColor = Color.White,
    unfocusedPlaceholderColor = Color.White,
    focusedPlaceholderColor = Color.White,
    containerColor = Color.Transparent,
    focusedIndicatorColor = Color.White,
    unfocusedIndicatorColor = Color.White,
    cursorColor = Color.White,
    errorContainerColor = Color.Transparent)
@Composable
fun AppIcon(){
    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(modifier = Modifier.size(150.dp),
            painter = painterResource(id = R.drawable.study_icon),
            contentDescription = "")
        Image(modifier = Modifier
            .height(35.dp)
            .width(142.dp),
            painter = painterResource(id = R.drawable.elive), contentDescription = "")
    }
}

@Composable
fun myNavigationDrawerItemColors() = NavigationDrawerItemDefaults.colors(
    unselectedContainerColor = Color.Transparent,
    unselectedIconColor = Color.White,
    unselectedTextColor = Color.White)