package com.tropat.elive.model.books

data class Books(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)