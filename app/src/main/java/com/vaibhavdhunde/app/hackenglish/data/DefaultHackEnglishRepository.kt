package com.vaibhavdhunde.app.hackenglish.data

import com.vaibhavdhunde.app.hackenglish.data.source.remote.UsersRemoteDataSource
import com.vaibhavdhunde.app.hackenglish.model.User

class DefaultHackEnglishRepository(
    private val remoteDataSource: UsersRemoteDataSource
) : HackEnglishRepository {

    override suspend fun registerUser(name: String, email: String, password: String): Result<User> {
        return remoteDataSource.registerUser(name, email, password)
    }

    override suspend fun loginUser(email: String, password: String): Result<User> {
        return remoteDataSource.loginUser(email, password)
    }
}