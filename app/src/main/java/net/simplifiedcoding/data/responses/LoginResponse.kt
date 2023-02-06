package net.simplifiedcoding.data.responses

data class LoginResponse(
    val access_token: String?,
    val message: String,
    val refresh_token: String,
    val smartband_code: String
)