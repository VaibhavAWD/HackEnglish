package com.vaibhavdhunde.app.hackenglish.data

import com.vaibhavdhunde.app.hackenglish.model.User

interface UsersDataSource {

    suspend fun registerUser(name: String, email: String, password: String): Result<User>

    suspend fun loginUser(email: String, password: String): Result<User>

}