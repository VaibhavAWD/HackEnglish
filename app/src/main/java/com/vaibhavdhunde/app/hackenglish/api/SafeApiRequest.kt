package com.vaibhavdhunde.app.hackenglish.api

import com.vaibhavdhunde.app.hackenglish.util.ApiException
import retrofit2.Response

object SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()

        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val message = "${response.message()}\nError Code: ${response.code()}"
            throw ApiException(message)
        }
    }
}