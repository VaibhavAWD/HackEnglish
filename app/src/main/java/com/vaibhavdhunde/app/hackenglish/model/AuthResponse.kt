package com.vaibhavdhunde.app.hackenglish.model

data class AuthResponse(
    val error: Boolean,
    val message: String?,
    val user: User?
)