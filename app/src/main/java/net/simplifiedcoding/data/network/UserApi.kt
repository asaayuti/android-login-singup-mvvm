package net.simplifiedcoding.data.network

import net.simplifiedcoding.data.responses.UserResponse
import retrofit2.http.GET

interface UserApi {

    @GET("/api/v1/atlets/my")
    suspend fun getUser(): UserResponse
}