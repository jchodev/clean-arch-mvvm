package com.jerry.clean_arch_mvvm.marketpage.presentation.viewmodel

import com.jerry.clean_arch_mvvm.base.presentation.UiState
import com.jerry.clean_arch_mvvm.base.usecase.UseCaseResult
import com.jerry.clean_arch_mvvm.sharedtest.MarketTestStubs
import com.jerry.clean_arch_mvvm.sharedtest.MarketTestStubs.Companion.testStr
import com.jerry.clean_arch_mvvm.marketpage.domain.entities.ui.MarketUiItem
import com.jerry.clean_arch_mvvm.marketpage.domain.usecase.GetMarketUseCase
import io.mockk.coEvery
import io.mockk.junit5.MockKExtension
import io.mockk.mockkClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.net.SocketTimeoutException

@ExperimentalCoroutinesApi
@MockKExtension.ConfirmVerification
class MarketViewModelTest {
    private lateinit var useCase: GetMarketUseCase
    private lateinit var viewModel: MarketViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        useCase = mockkClass(GetMarketUseCase::class)
    }

    @AfterEach
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test market view model ui state expected success state`() = runTest {
        val useCaseResult = UseCaseResult.Success(MarketTestStubs.testMarketUiItem)

        viewModel = MarketViewModel(
            getMarketUseCase = useCase,
            dispatcher = TestCoroutineDispatcher()
        )

        //assign
        coEvery { useCase(any()) } returns useCaseResult

        val events = mutableListOf<UiState<MarketUiItem>>()
        viewModel.uiState.onEach {
            events.add(it)
        }.launchIn(
            CoroutineScope(UnconfinedTestDispatcher(testScheduler))
        )

        viewModel.getMarketsByBaseId("")
        advanceUntilIdle()

        //verify
        Assertions.assertEquals(3, events.size)
        Assertions.assertEquals(UiState.Initial, events[0])
        Assertions.assertEquals(UiState.Loading, events[1])

        Assertions.assertEquals(
            MarketTestStubs.testMarketUiItem,
            (events[2] as UiState.Success<MarketUiItem>).data
        )
    }

    @Test
    fun `test market view model ui state expected Failure state`() = runTest {
        val useCaseResult = UseCaseResult.Failure(throwable = SocketTimeoutException())

        viewModel = MarketViewModel(
            getMarketUseCase = useCase,
            dispatcher = TestCoroutineDispatcher()
        )

        //assign
        coEvery { useCase(any()) } returns useCaseResult

        val events = mutableListOf<UiState<MarketUiItem>>()
        viewModel.uiState.onEach {
            events.add(it)
        }.launchIn(
            CoroutineScope(UnconfinedTestDispatcher(testScheduler))
        )

        viewModel.getMarketsByBaseId("")
        advanceUntilIdle()

        //verify
        Assertions.assertEquals(3, events.size)
        Assertions.assertEquals(UiState.Initial, events[0])
        Assertions.assertEquals(UiState.Loading, events[1])
        Assertions.assertTrue(events[2] is UiState.Failure)
    }

    @Test
    fun `test markett view model ui state expected Customer error state`() = runTest {
        val useCaseResult = UseCaseResult.CustomerError(testStr)

        viewModel = MarketViewModel(
            getMarketUseCase = useCase,
            dispatcher = TestCoroutineDispatcher()
        )

        //assign
        coEvery { useCase(any()) } returns useCaseResult

        val events = mutableListOf<UiState<MarketUiItem>>()
        viewModel.uiState.onEach {
            events.add(it)
        }.launchIn(
            CoroutineScope(UnconfinedTestDispatcher(testScheduler))
        )

        viewModel.getMarketsByBaseId("")
        advanceUntilIdle()

        //verify
        Assertions.assertEquals(3, events.size)
        Assertions.assertEquals(UiState.Initial, events[0])
        Assertions.assertEquals(UiState.Loading, events[1])
        Assertions.assertTrue(events[2] is UiState.CustomerError)
        Assertions.assertEquals(
            useCaseResult.error,
            (events[2] as UiState.CustomerError).errorMessage
        )
    }
}