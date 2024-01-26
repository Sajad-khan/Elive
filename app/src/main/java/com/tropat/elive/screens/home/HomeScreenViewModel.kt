package com.tropat.elive.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.tropat.elive.model.EliveUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel: ViewModel() {
    private val id = FirebaseAuth.getInstance().currentUser?.uid
    private val db = Firebase.firestore
    private val docRef = db.collection("users").document(id!!)
    private val _data = MutableStateFlow<EliveUser?>(null)
    val data: StateFlow<EliveUser?> = _data

    init {
        getUser()
    }

    private fun getUser()
     = viewModelScope.launch {
         docRef.get().addOnSuccessListener {
             val user = it.toObject<EliveUser>()
             if (user != null) {
                _data.value = user
             }
             Log.d("FB_USER", user.toString())
         }
    }
}