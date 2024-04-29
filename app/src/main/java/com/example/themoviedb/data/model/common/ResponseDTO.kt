package com.example.themoviedb.data.model.common

import com.google.gson.annotations.SerializedName


data class ResponseDTO<T>(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val result: ArrayList<T>?,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalEntries: Int
)