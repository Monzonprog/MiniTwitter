package com.jmonzon.minitwitter.Model

data class RequestSignup(
    val code: String,
    val email: String,
    val password: String,
    val username: String
)