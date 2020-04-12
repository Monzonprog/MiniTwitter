package com.jmonzon.minitwitter.models

data class ResponseAuth(
    val active: Boolean,
    val created: String,
    val email: String,
    val photoUrl: String,
    val token: String,
    val username: String
)