package br.com.phi.challenge.api

/**
 * Created by pcamilo on 10/01/2021.
 */
sealed class Result<out T: Any> {
    data class Success<out T : Any>(val data: T? = null) : Result<T>()
    data class Error(val errorMessage: String? = null) : Result<Nothing>()
}