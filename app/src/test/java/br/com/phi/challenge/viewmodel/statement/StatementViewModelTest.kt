package br.com.phi.challenge.viewmodel.statement

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.phi.challenge.api.Result
import br.com.phi.challenge.api.balance.data.local.BalancePreferences
import br.com.phi.challenge.api.statement.data.model.StatementItemResponse
import br.com.phi.challenge.api.statement.data.remote.StatementRepositoryImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.math.BigDecimal
import br.com.phi.challenge.api.balance.data.model.BalanceResponse
import br.com.phi.challenge.api.balance.data.remote.BalanceRepositoryImp
import br.com.phi.challenge.api.statement.data.model.StatementResponse
import org.junit.Assert.*
import org.mockito.Mockito.*

/**
 * Created by pcamilo on 11/01/2021.
 */
@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class StatementViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var statementRepo : StatementRepositoryImp

    @Mock
    private lateinit var balanceRepo : BalanceRepositoryImp

    @Mock
    private lateinit var balancePreferences: BalancePreferences

    lateinit var viewModel: StatementViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = StatementViewModel(balanceRepo, statementRepo, balancePreferences)
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test fun `Test get balance with SUCCESS`()  = runBlockingTest {
        //Given
        `when`(balanceRepo.getBalance()).thenReturn(getBalanceSuccess())

        //Execute
        viewModel.getBalance()

        //Expected
        assertNotNull(viewModel.balance.value)
    }

    @Test fun `Test get balance with ERROR`()  = runBlockingTest {
        //Given
        `when`(balanceRepo.getBalance()).thenReturn(getMockError())

        //Execute
        viewModel.getBalance()

        //Expected
        assertNull(viewModel.balance.value)
    }

    @Test fun `Test all statements with SUCCESS`()  = runBlockingTest {
        //Given
        `when`(statementRepo.getStatements(offset = 1)).thenReturn(getStatementsSuccess())

        //Execute
        viewModel.getStatements()

        //Expected
        assertTrue(viewModel.hasStatements.get() == VISIBLE)
    }

    @Test fun `Test all statements with ERROR`()  = runBlockingTest {
        //Given
        `when`(statementRepo.getStatements(offset = 0)).thenReturn(getMockError())

        //Execute
        viewModel.getStatements()

        //Expected
        assertTrue(viewModel.hasStatements.get() == GONE)
    }

    @Test fun `Test no more items to load`() = runBlockingTest {
        //Given
        viewModel.moreItemsToShow.set(false)

        //Execute
        viewModel.loadMoreItems()

        //Expected
        verifyNoMoreInteractions(statementRepo)
    }

    @Test fun `Test has more items to load`() = runBlockingTest {
        //Given
        viewModel.moreItemsToShow.set(true)

        //Execute
        viewModel.loadMoreItems()

        //Expected
        verify(statementRepo).getStatements(offset = 1)
    }

    @Test fun `Test hidden balance`() = runBlockingTest {
        //Given
        `when`(balancePreferences.balanceIsVisible).thenReturn(VISIBLE)

        //Execute
        viewModel.updateBalanceVisibility()

        //Expected
        assertTrue(viewModel.balanceVisibility.get() == GONE)
    }

    @Test fun `Test show balance`() = runBlockingTest {
        //Given
        `when`(balancePreferences.balanceIsVisible).thenReturn(GONE)

        //Execute
        viewModel.updateBalanceVisibility()

        //Expected
        assertTrue(viewModel.balanceVisibility.get() == VISIBLE)
    }

    private fun getBalanceSuccess() = Result.Success(BalanceResponse(BigDecimal(123)))
    private fun getStatementsSuccess() = Result.Success(StatementResponse(arrayListOf(StatementItemResponse("1", BigDecimal(123), "", "", "", "", ""))))
    private fun getMockError() = Result.Error("Something is wrong")

}