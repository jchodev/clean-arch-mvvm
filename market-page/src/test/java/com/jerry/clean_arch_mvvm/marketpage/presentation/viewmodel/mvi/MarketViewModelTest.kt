package com.jerry.clean_arch_mvvm.marketpage.presentation.viewmodel.mvi

import android.util.Log
import app.cash.turbine.test
import com.jerry.clean_arch_mvvm.base.presentation.UiState
import com.jerry.clean_arch_mvvm.base.usecase.UseCaseResult
import com.jerry.clean_arch_mvvm.marketpage.domain.entities.ui.MarketUiItem
import com.jerry.clean_arch_mvvm.marketpage.domain.usecase.GetMarketUseCase
import com.jerry.clean_arch_mvvm.marketpage.presentation.mvi.MarketIntent
import com.jerry.clean_arch_mvvm.sharedtest.MarketTestStubs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.net.SocketTimeoutException

@OptIn(ExperimentalCoroutinesApi::class)
class MarketViewModelTest {

    private lateinit var useCase: GetMarketUseCase
    private lateinit var viewModel: MarketViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        useCase = mockkClass(GetMarketUseCase::class)

        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0

        viewModel = MarketViewModel(
            getMarketUseCase = useCase
        )
        viewModel.initIntent()
    }

    @AfterEach
    fun after() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `test market view model ui state expected success state`() = runTest {
        val useCaseResult = UseCaseResult.Success(MarketTestStubs.testMarketUiItem)
        //assign
        coEvery { useCase.invoke(any()) } returns useCaseResult

        // Act
        val job = launch {
            viewModel.uiState.test {
                //verify
                Assertions.assertEquals(UiState.Initial, awaitItem())
                Assertions.assertEquals(UiState.Loading, awaitItem())
                Assertions.assertEquals(
                    MarketTestStubs.testMarketUiItem,
                    (awaitItem() as UiState.Success<MarketUiItem>).data
                )
            }
        }

        viewModel.sendIntent(MarketIntent.Initial(baseId = ""))

        // Assert
        job.join()
    }

    @Test
    fun `test market view model ui state expected Failure state`() = runTest {
        val useCaseResult = UseCaseResult.Failure(throwable = SocketTimeoutException())

        //assign
        coEvery { useCase.invoke(any()) } returns useCaseResult

        // Act
        val job = launch {
            viewModel.uiState.test {
                //verify
                Assertions.assertEquals(UiState.Initial, awaitItem())
                Assertions.assertEquals(UiState.Loading, awaitItem())
                Assertions.assertTrue(
                    awaitItem() is UiState.Failure
                )
            }
        }

        viewModel.sendIntent(MarketIntent.Initial(baseId = ""))

        // Assert
        job.join()
    }

    @Test
    fun `test markert view model ui state expected Customer error state`() = runTest {
        val useCaseResult = UseCaseResult.CustomerError(MarketTestStubs.testStr)

        //assign
        coEvery { useCase.invoke(any()) } returns useCaseResult

        // Act
        val job = launch {
            viewModel.uiState.test {
                //verify
                Assertions.assertEquals(UiState.Initial, awaitItem())
                Assertions.assertEquals(UiState.Loading, awaitItem())
                val customerError = awaitItem()
                Assertions.assertTrue(
                    customerError is UiState.CustomerError
                )
                Assertions.assertEquals(
                    useCaseResult.error,
                    (customerError as UiState.CustomerError).errorMessage
                )
            }
        }

        viewModel.sendIntent(MarketIntent.Initial(baseId = ""))

        // Assert
        job.join()
    }
}