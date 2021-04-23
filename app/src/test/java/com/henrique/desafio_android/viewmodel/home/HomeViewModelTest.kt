package com.henrique.desafio_android.viewmodel.home

import android.content.res.Resources
import android.os.Build
import com.henrique.desafio_android.domain.model.RequestState
import com.henrique.desafio_android.domain.model.balance.BalanceResponse
import com.henrique.desafio_android.domain.model.movimentation.MovimentationResponse
import com.henrique.desafio_android.domain.model.movimentation.MovimentationResponseList
import com.henrique.desafio_android.domain.repository.GetBalanceInteractor
import com.henrique.desafio_android.domain.repository.GetMovimentationInteractor
import com.henrique.desafio_android.domain.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import com.henrique.desafio_android.domain.utils.formatCurrency
import io.mockk.*
import java.math.BigDecimal

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class HomeViewModelTest {

    private lateinit var sut: HomeViewModel
    private val balanceInteractor: GetBalanceInteractor = mockk(relaxed = true)
    private val movimentationInteractor: GetMovimentationInteractor = mockk(relaxed = true)
    private val repositoryMock = mockk<Repository>()

    @Before
    fun setUp() {
        sut = HomeViewModel(balanceInteractor, movimentationInteractor)
    }

    @Test
    fun `should get balance and movimentations on resume when request state in movimentation interactor isn't loading`() {
        every { movimentationInteractor.requestState.value } returns RequestState.Idle
        sut.resume()

        verify {
            balanceInteractor.getBalance()
            movimentationInteractor.getMovimentation("10", "0")
        }
    }

    @Test
    fun `should get balance on resume when request state in movimentation interactor is loading`() {
        every { movimentationInteractor.requestState.value } returns RequestState.Loading
        sut.resume()

        verify {
            balanceInteractor.getBalance()
        }
    }

    @Test
    fun `alter balance visibility on toggleBalanceVisibility`() {
        sut.isBalanceVisible.observeForever { }
        sut.isBalanceVisible.value = false

        sut.toggleBalanceVisibility()

        assertEquals(true, sut.isBalanceVisible.value)
        sut.isBalanceVisible.removeObserver { }
    }

    @Test
    fun `should set balance amount text when get balance request is success`() {
        val balanceInteractorMock = getBalanceInteractor()
        val balanceAmountTextMock = "R$ 10"
        val balanceAmountMock = BigDecimal.TEN

        sut = HomeViewModel(balanceInteractorMock, movimentationInteractor)

        mockkStatic("com.henrique.desafio_android.domain.utils.ExtensionsKt")
        every { balanceAmountMock.formatCurrency() } returns balanceAmountTextMock

        sut.requestState.observeForever { }
        sut.balanceAmountText.observeForever { }

        coEvery { repositoryMock.getBalance() } returns getBalanceResponse()

        balanceInteractorMock.getBalance()

        balanceInteractorMock.requestState.value = RequestState.Success(getBalanceResponse())

        assertEquals(balanceAmountTextMock, sut.balanceAmountText.value)

        sut.requestState.removeObserver { }
        sut.balanceAmountText.removeObserver { }
    }

    private fun getBalanceInteractor(): GetBalanceInteractor {
        val coroutineScope = CoroutineScope(Dispatchers.Unconfined)
        val resourceMock: Resources = mockk(relaxed = true)

        return GetBalanceInteractor(repositoryMock, coroutineScope, resourceMock, mockk())
    }

    private fun getBalanceResponse(): BalanceResponse {
        return BalanceResponse(BigDecimal.TEN)
    }

    @Test
    fun `should set movimentation response when movimentation request is success`() {
        val movimentationInteractorMock = getMovimentationInteractor()
        val movimentationResponseList = getMovimentationResponseList()

        sut = HomeViewModel(balanceInteractor, movimentationInteractorMock)

        sut.requestState.observeForever { }
        sut.myMovimentationResponse.observeForever { }

        coEvery { repositoryMock.getMovimentation("", "") } returns movimentationResponseList

        movimentationInteractorMock.getMovimentation("", "")

        movimentationInteractorMock.requestState.value =
            RequestState.Success(movimentationResponseList)

        assertEquals(movimentationResponseList, sut.myMovimentationResponse.value)

        sut.requestState.removeObserver { }
        sut.myMovimentationResponse.removeObserver { }
    }

    private fun getMovimentationInteractor(): GetMovimentationInteractor {
        val coroutineScope = CoroutineScope(Dispatchers.Unconfined)
        val resourceMock: Resources = mockk(relaxed = true)

        return GetMovimentationInteractor(repositoryMock, coroutineScope, resourceMock, mockk())
    }

    private fun getMovimentationResponseList(): MovimentationResponseList {
        val movimentationResponse = MovimentationResponse(
            "",
            "",
            BigDecimal.TEN,
            "",
            "",
            "",
            "",
            "",
            ""
        )
        return MovimentationResponseList(listOf(movimentationResponse))
    }

}