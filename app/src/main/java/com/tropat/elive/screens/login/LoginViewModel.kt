package com.tropat.elive.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.tropat.elive.model.EliveUser
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    fun loginUser(
        email: String,
        password: String,
        navigateToNextScreen: () -> Unit,
        showErrorToast: (String) -> Unit
    )
    = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {task ->
                    if(task.isSuccessful){
                        navigateToNextScreen()
                    }
                    else{
                        task.exception?.message?.let { showErrorToast(it) }
                        Log.d("FB", task.exception?.message.toString())
                    }
                }
        }
        catch(e: Exception){
            Log.d("FB", "Login problem : $e")
        }
    }

    fun createUser(email: String, password: String,
                   navigateToNextScreen: () -> Unit,
                   showErrorToast: (String) -> Unit,
                   firstName: String,
                   lastName: String)
        = viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {task ->
                    if(task.isSuccessful){
                        createUserWithName(firstName, lastName)
                        navigateToNextScreen()
                    }
                    else{
                        task.exception?.message?.let { showErrorToast(it) }
                    }
                }
    }

    private fun createUserWithName(firstName: String, lastName: String) {
        val userId = auth.currentUser?.uid
        val user = EliveUser(
            userId = userId.toString(),
            firstName = firstName,
            lastName = lastName,
            avatarUrl = ""
        )
        user.toMap()
        if (userId != null) {
            FirebaseFirestore.getInstance().collection("users")
                .document(userId)
                .set(user)
                .addOnSuccessListener {

                }
                .addOnFailureListener {

                }
        }
    }
}