package com.jmonzon.minitwitter.models

data class Tweet(
    val id: Int,
    val likes: List<Likes>,
    val mensaje: String,
    val user: User
)