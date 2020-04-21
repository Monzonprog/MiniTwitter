package com.jmonzon.minitwitter.models

data class RequestSignup(
    val username: String,
    val email: String,
    val password: String,
    val code: String
)