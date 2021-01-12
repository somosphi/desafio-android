package br.com.phi.challenge.viewmodel.statementdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.phi.challenge.api.statement.data.model.StatementItemResponse
import br.com.phi.challenge.api.statement.data.remote.StatementRepositoryImp
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.math.BigDecimal
import br.com.phi.challenge.api.Result

/**
 * Created by pcamilo on 11/01/2021.
 */
@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class StatementDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var statementRepo : StatementRepositoryImp
    lateinit var viewModel: StatementDetailViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = StatementDetailViewModel(statementRepo)
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test fun `Test get statement by ID with SUCCESS`()  = runBlockingTest {
        //Given
        Mockito.`when`(statementRepo.getStatementDetail("1")).thenReturn(getMockSuccess())

        //Execute
        viewModel.getStatementDetail("1")

        //Expected
        Assert.assertNotNull(viewModel.statementDetail.value)
    }

    @Test fun `Test get statement by ID with ERROR`()  = runBlockingTest {
        //Given
        Mockito.`when`(statementRepo.getStatementDetail("0")).thenReturn(getMockError())

        //Execute
        viewModel.getStatementDetail("0")

        //Expected
        Assert.assertNull(viewModel.statementDetail.value)
    }

    private fun getMockSuccess() = Result.Success(StatementItemResponse("1", BigDecimal(123), "", "", "", "", ""))
    private fun getMockError() = Result.Error("Something is wrong")

}