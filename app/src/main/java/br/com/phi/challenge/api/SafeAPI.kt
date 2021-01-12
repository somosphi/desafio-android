package br.com.phi.challenge.api

import retrofit2.Response

/**
 * Created by pcamilo on 10/01/2021.
 */
suspend fun <T : Any> executeApi(call: suspend () -> Response<T>): Result<T> {
    return try {
        val response = call.invoke()
        if (response.isSuccessful) {
            Result.Success(response.body())
        } else {
            Result.Error(response.message())
        }

    } catch (e: Exception) {
        Result.Error(e.message)
    }
}