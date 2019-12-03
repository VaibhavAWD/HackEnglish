package com.vaibhavdhunde.app.hackenglish.data.source.remote

import com.vaibhavdhunde.app.hackenglish.api.HackEnglishApi
import com.vaibhavdhunde.app.hackenglish.api.SafeApiRequest
import com.vaibhavdhunde.app.hackenglish.data.Result
import com.vaibhavdhunde.app.hackenglish.data.Result.Error
import com.vaibhavdhunde.app.hackenglish.data.Result.Success
import com.vaibhavdhunde.app.hackenglish.data.UsersDataSource
import com.vaibhavdhunde.app.hackenglish.model.User
import com.vaibhavdhunde.app.hackenglish.util.ApiException
import com.vaibhavdhunde.app.hackenglish.util.NetworkException

class UsersRemoteDataSource(
    private val api: HackEnglishApi
) : UsersDataSource {

    override suspend fun registerUser(name: String, email: String, password: String): Result<User> {
        return try {
            val response = SafeApiRequest.apiRequest { api.registerUser(name, email, password) }
            if (response.error) {
                Error(response.message!!)
            } else {
                Success(response.user!!)
            }
        } catch (e: NetworkException) {
            Error(e.message!!)
        } catch (e: ApiException) {
            Error(e.message!!)
        }
    }

}