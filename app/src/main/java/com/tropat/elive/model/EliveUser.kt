package com.tropat.elive.model

data class EliveUser(
    var userId: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var avatarUrl: String = ""
){
    fun toMap(): MutableMap<String, Any>{
        return mutableMapOf(
            "user_id" to this.userId,
            "first_name" to this.firstName,
            "last_name" to this.lastName,
            "avatar_url" to this.avatarUrl
        )
    }
}
