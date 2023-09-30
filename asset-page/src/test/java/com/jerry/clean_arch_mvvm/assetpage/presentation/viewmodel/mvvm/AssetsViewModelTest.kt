package com.jerry.clean_arch_mvvm.assetpage.presentation.viewmodel.mvvm

import app.cash.turbine.test
import com.jerry.clean_arch_mvvm.sharedtest.AssetsTestStubs
import com.jerry.clean_arch_mvvm.sharedtest.AssetsTestStubs.Companion.testAssetUiItem
import com.jerry.clean_arch_mvvm.assetpage.domain.entities.ui.AssetUiItem
import com.jerry.clean_arch_mvvm.assetpage.domain.usecase.GetAssetsUseCase
import com.jerry.clean_arch_mvvm.base.presentation.UiState
import com.jerry.clean_arch_mvvm.base.usecase.UseCaseResult
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
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.net.SocketTimeoutException

@OptIn(ExperimentalCoroutinesApi::class)
class AssetsViewModelTest {

    private lateinit var getAssetsUseCase: GetAssetsUseCase
    private lateinit var assetsViewModel: AssetsViewModel


    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        getAssetsUseCase = mockkClass(GetAssetsUseCase::class)

        assetsViewModel = AssetsViewModel(
            getAssetsUseCase = getAssetsUseCase
        )
    }

    @AfterEach
    fun after(){
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `test assets view model ui state expected success state`() = runTest {
        val useCaseResult = UseCaseResult.Success(listOf(
            testAssetUiItem
        ))

        //assign
        coEvery { getAssetsUseCase.invoke() } returns useCaseResult

        //action
        assetsViewModel.getAssetList()

        assetsViewModel.uiState.test {
            //verify
            assertEquals(UiState.Initial, awaitItem())
            assertEquals(UiState.Loading,  awaitItem())
            assertEquals( useCaseResult.data.size, ( awaitItem() as UiState.Success<List<AssetUiItem>>).data.size )
        }

    }

    @Test
    fun `test assets view model ui state expected Failure state`() = runTest {
        val useCaseResult = UseCaseResult.Failure(throwable = SocketTimeoutException())

        //assign
        coEvery { getAssetsUseCase.invoke() } returns useCaseResult

        //action
        assetsViewModel.getAssetList()

        //verify
        assetsViewModel.uiState.test {
            //verify
            assertEquals(UiState.Initial, awaitItem())
            assertEquals(UiState.Loading, awaitItem())
            assertTrue(awaitItem() is UiState.Failure)
        }
    }

    @Test
    fun `test assets view model ui state expected Customer error state`() = runTest {
        val useCaseResult = UseCaseResult.CustomerError(AssetsTestStubs.testStr)

        //assign
        coEvery { getAssetsUseCase.invoke() } returns useCaseResult

        //action
        assetsViewModel.getAssetList()

        //verify
        assetsViewModel.uiState.test {
            //verify
            assertEquals(UiState.Initial, awaitItem())
            assertEquals(UiState.Loading, awaitItem())
            val customerErrorStatue = awaitItem()
            assertTrue(customerErrorStatue is UiState.CustomerError)
            assertEquals(useCaseResult.error, (customerErrorStatue as UiState.CustomerError).errorMessage)
        }
    }
}