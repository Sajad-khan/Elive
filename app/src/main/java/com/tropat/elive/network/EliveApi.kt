package com.tropat.elive.network

import com.tropat.elive.model.books.Books
import com.tropat.elive.model.books.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface EliveApi {
    @GET("volumes")
    suspend fun getBooks(
        @Query("q") query: String
    ): Books

    @GET("volumes/{bookId}")
    suspend fun getBookById(
        @Path("bookId") bookId: String
    ): Item
}