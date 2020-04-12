package com.jmonzon.minitwitter.models

data class RequestSignup(
    val code: String,
    val email: String,
    val password: String,
    val username: String
)