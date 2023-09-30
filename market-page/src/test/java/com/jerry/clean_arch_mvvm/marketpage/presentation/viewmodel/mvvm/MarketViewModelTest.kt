package com.jerry.clean_arch_mvvm.marketpage.presentation.viewmodel.mvvm

import app.cash.turbine.test
import com.jerry.clean_arch_mvvm.base.presentation.UiState
import com.jerry.clean_arch_mvvm.base.usecase.UseCaseResult
import com.jerry.clean_arch_mvvm.sharedtest.MarketTestStubs
import com.jerry.clean_arch_mvvm.sharedtest.MarketTestStubs.Companion.testStr
import com.jerry.clean_arch_mvvm.marketpage.domain.entities.ui.MarketUiItem
import com.jerry.clean_arch_mvvm.marketpage.domain.usecase.GetMarketUseCase
import com.jerry.clean_arch_mvvm.marketpage.presentation.viewmodel.mvvm.MarketViewModel
import io.mockk.clearAllMocks
import io.mockk.coEvery

import io.mockk.mockkClass

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

        viewModel = MarketViewModel(
            getMarketUseCase = useCase
        )
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
        coEvery { useCase(any()) } returns useCaseResult

        //action
        viewModel.getMarketsByBaseId("")

        //verify
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

    @Test
    fun `test market view model ui state expected Failure state`() = runTest {
        val useCaseResult = UseCaseResult.Failure(throwable = SocketTimeoutException())

        //assign
        coEvery { useCase(any()) } returns useCaseResult

        //action
        viewModel.getMarketsByBaseId("")

        //verify
        viewModel.uiState.test {
            //verify
            Assertions.assertEquals(UiState.Initial, awaitItem())
            Assertions.assertEquals(UiState.Loading, awaitItem())
            Assertions.assertTrue(
                awaitItem() is UiState.Failure
            )
        }

    }

    @Test
    fun `test markett view model ui state expected Customer error state`() = runTest {
        val useCaseResult = UseCaseResult.CustomerError(testStr)

        //assign
        coEvery { useCase(any()) } returns useCaseResult

        //action
        viewModel.getMarketsByBaseId("")

        //verify
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
}