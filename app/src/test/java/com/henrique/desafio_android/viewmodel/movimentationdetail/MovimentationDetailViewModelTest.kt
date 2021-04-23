package com.henrique.desafio_android.viewmodel.movimentationdetail

import android.content.res.Resources
import android.os.Build
import com.henrique.desafio_android.domain.model.RequestState
import com.henrique.desafio_android.domain.model.movimentation.MovimentationResponse
import com.henrique.desafio_android.domain.repository.GetMovimentationInteractor
import com.henrique.desafio_android.domain.repository.Repository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.junit.Assert.assertEquals
import java.math.BigDecimal

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class MovimentationDetailViewModelTest {

    private lateinit var sut: MovimentationDetailViewModel
    private val movimentationInteractor: GetMovimentationInteractor = mockk(relaxed = true)
    private val repositoryMock = mockk<Repository>()

    @Before
    fun setUp() {
        sut = MovimentationDetailViewModel(movimentationInteractor)
    }

    @Test
    fun `should close activity on back click`() {
        sut.onBackClick()
        assertEquals(true, sut.shouldCloseActivity.value)
    }

    @Test
    fun `should share receipt on share click`() {
        sut.onShareClick()
        assertEquals(true, sut.shouldShareActivity.value)
    }

    @Test
    fun `should get movimentation detail on getStatementDetail execute`() {
        sut.movimentationId.value = ""
        sut.getStatementDetail()

        verify {
            movimentationInteractor.getMovimentationDetail("")
        }
    }

    @Test
    fun `should set movimentation type when movimentation request is success`() {
        val movimentationInteractorMock = getMovimentationInteractor()
        val movimentationResponse = getMovimentationResponse()

        sut = MovimentationDetailViewModel(movimentationInteractorMock)

        sut.requestState.observeForever { }
        sut.movimentationType.observeForever { }

        coEvery { repositoryMock.getMovimentationDetail("") } returns movimentationResponse

        movimentationInteractorMock.getMovimentationDetail("")

        movimentationInteractorMock.requestStateDetail.value =
            RequestState.Success(movimentationResponse)

        assertEquals("Transferência Recebida", sut.movimentationType.value)

        sut.requestState.removeObserver { }
        sut.movimentationType.removeObserver { }
    }

    private fun getMovimentationInteractor(): GetMovimentationInteractor {
        val coroutineScope = CoroutineScope(Dispatchers.Unconfined)
        val resourceMock: Resources = mockk(relaxed = true)

        return GetMovimentationInteractor(repositoryMock, coroutineScope, resourceMock, mockk())
    }

    private fun getMovimentationResponse(): MovimentationResponse {
        return MovimentationResponse(
            "",
            "",
            BigDecimal.TEN,
            "",
            "",
            "Transferência Recebida",
            "",
            "",
            ""
        )
    }
}