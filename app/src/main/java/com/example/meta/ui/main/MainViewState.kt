package com.example.meta.ui.main

import com.example.meta.data.model.AmountResponse
import com.example.meta.data.model.DetailsResponse
import com.example.meta.data.model.ItemsResponse
import retrofit2.Call

sealed class MainViewState {
    data class ShowAmount(val amountResponse: AmountResponse) : MainViewState()
    data class ShowStatement(val itemsResponse: ItemsResponse) : MainViewState()
    data class ShowStatementDetails(val detailsResponse: DetailsResponse) : MainViewState()
    data class ShowLoading(val isLoading: Boolean) : MainViewState()
    data class ShowError(val isRequestError: Boolean) : MainViewState()
}