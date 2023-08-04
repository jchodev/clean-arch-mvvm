package com.jerry.clean_arch_mvvm.assetpage.presentation.viewmodel

import com.jerry.clean_arch_mvvm.assetpage.AssetsTestStubs
import com.jerry.clean_arch_mvvm.assetpage.AssetsTestStubs.Companion.testAssetUiItem
import com.jerry.clean_arch_mvvm.assetpage.domain.entities.ui.AssetUiItem
import com.jerry.clean_arch_mvvm.assetpage.domain.usecase.GetAssetsUseCase
import com.jerry.clean_arch_mvvm.base.presentation.UiState
import com.jerry.clean_arch_mvvm.base.usecase.UseCaseResult
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
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.net.SocketTimeoutException

@ExperimentalCoroutinesApi
@MockKExtension.ConfirmVerification
class AssetsViewModelTest {

    private lateinit var getAssetsUseCase: GetAssetsUseCase
    private lateinit var assetsViewModel: AssetsViewModel


    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        getAssetsUseCase = mockkClass(GetAssetsUseCase::class)

    }

    @AfterEach
    fun after(){
        Dispatchers.resetMain()
    }

    @Test
    fun `test assets view model ui state expected success state`() = runTest {
        val useCaseResult = UseCaseResult.Success(listOf(
            testAssetUiItem
        ))

        assetsViewModel = AssetsViewModel(
            getAssetsUseCase = getAssetsUseCase,
            dispatcher = TestCoroutineDispatcher()
        )

        //assign
        coEvery { getAssetsUseCase() } returns useCaseResult

        val events = mutableListOf<UiState<List<AssetUiItem>>>()
        assetsViewModel.uiState.onEach {
            events.add(it)
        }.launchIn(
            CoroutineScope(UnconfinedTestDispatcher(testScheduler))
        )

        assetsViewModel.getAssetList()

        advanceUntilIdle()
        //verify
        assertEquals(3, events.size)
        assertEquals(UiState.Initial, events[0])
        assertEquals(UiState.Loading, events[1])
        assertEquals( useCaseResult.data.size, (events[2] as UiState.Success<List<AssetUiItem>>).data.size )
    }

    @Test
    fun `test assets view model ui state expected Failure state`() = runTest {
        val useCaseResult = UseCaseResult.Failure(throwable = SocketTimeoutException())

        assetsViewModel = AssetsViewModel(
            getAssetsUseCase = getAssetsUseCase,
            dispatcher = TestCoroutineDispatcher()
        )

        //assign
        coEvery { getAssetsUseCase() } returns useCaseResult

        val events = mutableListOf<UiState<List<AssetUiItem>>>()
        assetsViewModel.uiState.onEach {
            events.add(it)
        }.launchIn(
            CoroutineScope(UnconfinedTestDispatcher(testScheduler))
        )

        assetsViewModel.getAssetList()

        advanceUntilIdle()
        //verify
        assertEquals(3, events.size)
        assertEquals(UiState.Initial, events[0])
        assertEquals(UiState.Loading, events[1])
        assertTrue(events[2] is UiState.Failure)
    }

    @Test
    fun `test assets view model ui state expected Customer error state`() = runTest {
        val useCaseResult = UseCaseResult.CustomerError(AssetsTestStubs.testStr)

        assetsViewModel = AssetsViewModel(
            getAssetsUseCase = getAssetsUseCase,
            dispatcher = TestCoroutineDispatcher()
        )

        //assign
        coEvery { getAssetsUseCase() } returns useCaseResult

        val events = mutableListOf<UiState<List<AssetUiItem>>>()
        assetsViewModel.uiState.onEach {
            events.add(it)
        }.launchIn(
            CoroutineScope(UnconfinedTestDispatcher(testScheduler))
        )

        assetsViewModel.getAssetList()

        advanceUntilIdle()
        //verify
        assertEquals(3, events.size)
        assertEquals(UiState.Initial, events[0])
        assertEquals(UiState.Loading, events[1])
        assertTrue(events[2] is UiState.CustomerError)
        assertEquals(useCaseResult.error, (events[2] as UiState.CustomerError).errorMessage)
    }
}