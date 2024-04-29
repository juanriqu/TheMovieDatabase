package com.example.themoviedb.domain.model

data class ErrorModel(
    val code: Int,
    val httpStatus: Int?,
    val message: String
)