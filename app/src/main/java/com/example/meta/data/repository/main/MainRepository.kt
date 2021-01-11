package com.example.meta.data.repository.main

import com.example.meta.data.model.AmountResponse
import com.example.meta.data.model.DetailsResponse
import com.example.meta.data.model.ItemsResponse
import retrofit2.Call

interface MainRepository {
    suspend fun doRequestGetAmount(token: String, success : (AmountResponse) -> Unit, failure: () -> Unit)

    suspend fun doRequestGetStatement(token: String, limit: Int, offset: Int, success : (ItemsResponse) -> Unit, failure: () -> Unit)

    suspend fun doRequestGetDetailsStatement(token: String, id: String, success : (DetailsResponse) -> Unit, failure: () -> Unit)
}