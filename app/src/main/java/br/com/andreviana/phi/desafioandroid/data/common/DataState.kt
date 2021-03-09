package br.com.andreviana.phi.desafioandroid.data.common

sealed class DataState<out R> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Failure(val code: Int, val message: String) : DataState<Nothing>()
    data class Error(val throwable: Throwable) : DataState<Nothing>()
    object Loading : DataState<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[throwable=$throwable]"
            is Failure -> "Failure[code=$code, message=$message]"
            Loading -> "Loading"
        }
    }
}

val DataState<*>.succeeded
    get() = this is DataState.Success && data != null