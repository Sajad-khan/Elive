package com.tropat.elive.model

data class Book(
    var id: String = "",
    var title: String = "",
    var authors: List<String> = listOf(),
    var notes: String = ""
)