package com.tropat.elive.repository

import com.tropat.elive.data.Resource
import com.tropat.elive.model.books.Item
import com.tropat.elive.network.EliveApi
import javax.inject.Inject

class EliveBooksRepository @Inject constructor(private val api: EliveApi) {

    suspend fun getBooks(booksQuery: String): Resource<List<Item>>{
        return try {
            Resource.Loading(data = "Loading....")
            val itemList = api.getBooks(booksQuery).items
            if(itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)
        }
        catch (e: Exception){
            Resource.Error(message = e.message.toString())
        }
    }
    suspend fun getBookInfo(bookId: String): Resource<Item>{
        val response = try{
            Resource.Loading(data = true)
            api.getBookById(bookId)
        }
        catch(e: Exception){
            return Resource.Error(message = e.message.toString())
        }
        Resource.Loading(data = false)
        return Resource.Success(data = response)
    }
}