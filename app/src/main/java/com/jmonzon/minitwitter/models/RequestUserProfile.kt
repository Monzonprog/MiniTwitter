package com.jmonzon.minitwitter.models

data class RequestUserProfile(
    val descripcion: String,
    val email: String,
    val password: String,
    val username: String,
    val website: String
)