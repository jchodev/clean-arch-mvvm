package com.jerry.clean_arch_mvvm.marketpage.presentation.viewmodel.mvi

import app.cash.turbine.test
import com.jerry.clean_arch_mvvm.base.presentation.UiState
import com.jerry.clean_arch_mvvm.base.usecase.UseCaseResult
import com.jerry.clean_arch_mvvm.marketpage.domain.entities.ui.MarketUiItem
import com.jerry.clean_arch_mvvm.marketpage.domain.usecase.GetMarketUseCase
import com.jerry.clean_arch_mvvm.marketpage.presentation.mvi.MarketIntent
import com.jerry.clean_arch_mvvm.marketpage.presentation.viewmodel.mvi.MarketViewModel
import com.jerry.clean_arch_mvvm.sharedtest.MarketTestStubs
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
        viewModel.initIntent()
    }

    @AfterEach
    fun after() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun ddd() = runTest {

        val useCaseResult = UseCaseResult.Success(MarketTestStubs.testMarketUiItem)

        //assign
        coEvery { useCase(any()) } returns useCaseResult


        //verify
        viewModel.uiState.test {

            //action
            viewModel.sendIntent(MarketIntent.Initial(baseId = ""))

            //verify
            Assertions.assertEquals(UiState.Initial, awaitItem())
            Assertions.assertEquals(UiState.Loading, awaitItem())
            Assertions.assertEquals(
                MarketTestStubs.testMarketUiItem,
                (awaitItem() as UiState.Success<MarketUiItem>).data
            )
        }
    }
}