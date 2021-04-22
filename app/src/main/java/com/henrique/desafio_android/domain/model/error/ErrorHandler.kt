package com.henrique.desafio_android.domain.model.error

import android.content.res.Resources
import com.google.gson.Gson
import com.henrique.desafio_android.R
import retrofit2.HttpException
import java.lang.Exception

class ErrorHandler(private val resources: Resources, private val gson: Gson = Gson()) {

    companion object {
        private const val UNEXPECTED_ERROR_CODE = "NO-CODE"
    }

    fun buildErrorResponse(e: Exception) = if (e is HttpException) {
        parseToError(e.response()?.errorBody()?.string())
    } else {
        buildGenericErrorResponse()
    }

    private fun parseToError(backendErrorString: String?): ErrorResponse {
        return try {
            val deserializedError =
                gson.fromJson(backendErrorString.orEmpty(), ErrorResponse::class.java)
            requireNotNull(deserializedError.code)
            requireNotNull(deserializedError.message)
            return deserializedError
        } catch (ex: Exception) {
            buildGenericErrorResponse()
        }
    }

    private fun buildGenericErrorResponse(): ErrorResponse {
        return ErrorResponse(
            null,
            resources.getString(R.string.generic_error_message),
            UNEXPECTED_ERROR_CODE,
            null
        )
    }

}