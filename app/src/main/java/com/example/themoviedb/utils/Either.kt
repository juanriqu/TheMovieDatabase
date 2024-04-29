package com.example.themoviedb.utils

/**
This class used to handle results in a generic way, allowing you to define [Success], [Error] states for any type of data.
 */
sealed class Either<out T, out E> {
    /**
     * A class that represents a successful operation.
     */
    data class Success<out T>(val data: T) : Either<T, Nothing>()

    /**
     * A class that represents an error operation.
     */
    data class Error<out E>(val error: E) : Either<Nothing, E>()
}