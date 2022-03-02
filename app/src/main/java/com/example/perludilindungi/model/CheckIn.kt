package com.example.perludilindungi.model

data class CheckIn(
    val qrCode: String,
    val latitude: Double,
    val longitude: Double
)

data class CheckInResponse(
    val success: Boolean? = null,
    val code: Int? = null,
    val message: String? = null,
    val data: CheckInData? = null
)

data class CheckInData(
    val userStatus: String? = null,
    val reason: String? = null
)