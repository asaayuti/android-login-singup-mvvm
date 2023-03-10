package net.simplifiedcoding.data.network

import net.simplifiedcoding.data.responses.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @FormUrlEncoded
    @POST("/api/v1/auth/login")
    suspend fun login(
        @Field("username") email: String,
        @Field("password") password: String
    ) : LoginResponse
}