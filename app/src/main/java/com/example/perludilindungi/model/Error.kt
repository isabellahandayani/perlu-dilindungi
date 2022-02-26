package com.example.perludilindungi.model

data class ErrorGet(
    val curr_val: String,
    val message: String
)

data class ErrorPost(
    val code: Int,
    val message: String,
    val success: Boolean
)