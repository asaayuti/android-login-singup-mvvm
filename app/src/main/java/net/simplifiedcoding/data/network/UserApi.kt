package net.simplifiedcoding.data.network

import net.simplifiedcoding.data.responses.UserResponse
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {

    @GET("/api/v1/atlets/my")
    suspend fun getUser(): UserResponse

    @POST("/api/v1/auth/logout")
    suspend fun logout(): ResponseBody
}