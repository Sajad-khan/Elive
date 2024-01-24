package com.tropat.elive.utils

fun isEmailValid(email: String): Boolean {
    // Implement your email validation logic here
    val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$".toRegex()
    return email.matches(emailRegex)
}

// Password should have length of at least 8 characters, containing english letters, numbers, symbols
fun isPasswordValid(password: String): Boolean{
    val passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#\$%^&*()_+])[A-Za-z\\d!@#\$%^&*()_+]{8,}$".toRegex()
    return password.matches(passwordRegex)
}