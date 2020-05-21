package com.jmonzon.minitwitter.models

data class ResponseUserProfile(
    val created: String,
    val descripcion: String,
    val email: String,
    val id: Int,
    val photoUrl: String,
    val username: String,
    val website: String
)