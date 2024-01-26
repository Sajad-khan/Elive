package com.tropat.elive.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EliveAppBar(title: String, exitClicked: () -> Unit){
    TopAppBar(title = { Text(text = title,
        style = MaterialTheme.typography.titleLarge, color = Color.White)},
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        navigationIcon = { IconButton(onClick = exitClicked) {
            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back button",
                tint = Color.White)
        }})
}