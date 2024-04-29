package com.example.themoviedb.data.model.common

import com.example.themoviedb.domain.model.ErrorModel
import com.google.gson.annotations.SerializedName

/**
    * This class represents the error response from the API
    * @param code: The error code
    * @param httpStatus: The HTTP status code
    * @param message: The error message
 */
data class ErrorDTO(
    @SerializedName("status_code") val code: Int,
    val httpStatus: Int? = null,
    @SerializedName("status_message") val message: String,
) {
    companion object {

        /**
         * This function returns the error response based on the error code following the [TMDB API documentation](https://developer.themoviedb.org/docs/errors)
         */
        fun fromCode(code: Int): ErrorDTO {
            return when (code) {
                1 -> ErrorDTO(code, 200, "Success")
                2 -> ErrorDTO(code, 501, "Invalid service: this service does not exist.")
                3 -> ErrorDTO(code, 401, "Authentication failed: You do not have permissions to access the service.")
                4 -> ErrorDTO(code, 405, "Invalid format: This service doesn't exist in that format.")
                5 -> ErrorDTO(code, 422, "Invalid parameters: Your request parameters are incorrect.")
                6 -> ErrorDTO(code, 404, "Invalid id: The pre-requisite id is invalid or not found.")
                7 -> ErrorDTO(code, 401, "Invalid API key: You must be granted a valid key.")
                8 -> ErrorDTO(code, 403, "Duplicate entry: The data you tried to submit already exists.")
                9 -> ErrorDTO(code, 503, "Service offline: This service is temporarily offline, try again later.")
                10 -> ErrorDTO(code, 401, "Suspended API key: Access to your account has been suspended, contact TMDB.")
                11 -> ErrorDTO(code, 500, "Internal error: Something went wrong, contact TMDB.")
                12 -> ErrorDTO(code, 201, "The item/record was updated successfully.")
                13 -> ErrorDTO(code, 200, "The item/record was deleted successfully.")
                14 -> ErrorDTO(code, 401, "Authentication failed.")
                15 -> ErrorDTO(code, 500, "Failed.")
                16 -> ErrorDTO(code, 401, "Device denied.")
                17 -> ErrorDTO(code, 401, "Session denied.")
                18 -> ErrorDTO(code, 400, "Validation failed.")
                19 -> ErrorDTO(code, 406, "Invalid accept header.")
                20 -> ErrorDTO(code, 422, "Invalid date range: Should be a range no longer than 14 days.")
                21 -> ErrorDTO(code, 200, "Entry not found: The item you are trying to edit cannot be found.")
                22 -> ErrorDTO(code, 400, "Invalid page: Pages start at 1 and max at 500. They are expected to be an integer.")
                23 -> ErrorDTO(code, 400, "Invalid date: Format needs to be YYYY-MM-DD.")
                24 -> ErrorDTO(code, 504, "Your request to the backend server timed out. Try again.")
                25 -> ErrorDTO(code, 429, "Your request count (#) is over the allowed limit of (40).")
                26 -> ErrorDTO(code, 400, "You must provide a username and password.")
                27 -> ErrorDTO(code, 400, "Too many append to response objects: The maximum number of remote calls is 20.")
                28 -> ErrorDTO(code, 400, "Invalid timezone: Please consult the documentation for a valid timezone.")
                29 -> ErrorDTO(code, 400, "You must confirm this action: Please provide a confirm=true parameter.")
                30 -> ErrorDTO(code, 401, "Invalid username and/or password: You did not provide a valid login.")
                31 -> ErrorDTO(code, 401, "Account disabled: Your account is no longer active. Contact TMDB if this is an error.")
                32 -> ErrorDTO(code, 401, "Email not verified: Your email address has not been verified.")
                33 -> ErrorDTO(code, 401, "Invalid request token: The request token is either expired or invalid.")
                34 -> ErrorDTO(code, 404, "The resource you requested could not be found.")
                35 -> ErrorDTO(code, 401, "Invalid token.")
                36 -> ErrorDTO(code, 401, "This token hasn't been granted write permission by the user.")
                37 -> ErrorDTO(code, 404, "The requested session could not be found.")
                38 -> ErrorDTO(code, 401, "You don't have permission to edit this resource.")
                39 -> ErrorDTO(code, 401, "This resource is private.")
                40 -> ErrorDTO(code, 200, "Nothing to update.")
                41 -> ErrorDTO(code, 422, "This request token hasn't been approved by the user.")
                42 -> ErrorDTO(code, 405, "This request method is not supported for this resource.")
                43 -> ErrorDTO(code, 502, "Couldn't connect to the backend server.")
                44 -> ErrorDTO(code, 500, "The ID is invalid.")
                45 -> ErrorDTO(code, 403, "This user has been suspended.")
                46 -> ErrorDTO(code, 503, "The API is undergoing maintenance. Try again later.")
                47 -> ErrorDTO(code, 400, "The input is not valid.")
                else -> ErrorDTO(code, 500, "Unknown error")
            }
        }

        /**
         * This function returns the error response based on the error body, because Retrofit doesn't parse the error body by default
         */
        fun fromErrorBody(errorBody: String?): ErrorDTO {
            errorBody?.let {
                val statusCodeIndex = it.indexOf("\"status_code\":")
                if (statusCodeIndex != -1) {
                    val statusCodeSubstring = it.substring(statusCodeIndex + "\"status_code\":".length)
                    val statusCodeEndIndex = statusCodeSubstring.indexOf(",")
                    if (statusCodeEndIndex != -1) {
                        return fromCode(statusCodeSubstring.substring(0, statusCodeEndIndex).trim().toIntOrNull() ?: -1)
                    }
                }
            }
            return ErrorDTO(500, 500, "Unknown error")
        }
    }
}

fun ErrorDTO.toErrorModel(): ErrorModel {
    ErrorDTO.fromCode(code).let {
        return ErrorModel(it.code, it.httpStatus, it.message)
    }
}