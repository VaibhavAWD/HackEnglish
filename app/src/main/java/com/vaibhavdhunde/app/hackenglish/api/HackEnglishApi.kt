package com.vaibhavdhunde.app.hackenglish.api

import com.vaibhavdhunde.app.hackenglish.model.AuthResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface HackEnglishApi {

    @FormUrlEncoded
    @POST("register")
    suspend fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<AuthResponse>

    companion object {
        operator fun invoke(interceptor: NetworkInterceptor): HackEnglishApi {
            val apiClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            return Retrofit.Builder()
                .client(apiClient)
                .baseUrl("http://192.168.43.41/myapis/hackenglish/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HackEnglishApi::class.java)
        }
    }
}