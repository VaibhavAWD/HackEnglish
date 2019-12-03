package com.vaibhavdhunde.app.hackenglish.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val password_hash: String,
    val api_key: String,
    val created_at: String
)