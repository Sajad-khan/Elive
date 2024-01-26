package com.tropat.elive.screens.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tropat.elive.data.Resource
import com.tropat.elive.model.books.Item
import com.tropat.elive.repository.EliveBooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(private val repository: EliveBooksRepository): ViewModel(){
    var list: List<Item> by mutableStateOf(listOf())
    var isLoading by mutableStateOf(false)

    fun getBooks(query: String) {
        viewModelScope.launch {
            if(query.isEmpty()) return@launch
            try {
                isLoading = true
                when(val response = repository.getBooks(query)){
                    is Resource.Success -> {
                        list = response.data!!
                        if(list.isNotEmpty()) isLoading = false
                    }
                    is Resource.Error -> {
                        Log.d("NW", "getBooks: Failed to load books from api")
                    }
                    else -> {

                    }
                }
            }
            catch (e: Exception){
                Log.d("NW", "getBooks: ${e.message}")
            }
        }
    }
}