package com.jerry.clean_arch_mvvm.assetpage.presentation.viewmodel

import com.jerry.clean_arch_mvvm.assetpage.AssetsTestStubs
import com.jerry.clean_arch_mvvm.assetpage.data.mapper.AssetMapper
import com.jerry.clean_arch_mvvm.assetpage.domain.entities.ui.AssetUiItem
import com.jerry.clean_arch_mvvm.assetpage.domain.usecase.GetAssetsUseCase
import com.jerry.clean_arch_mvvm.base.presentation.UiState
import com.jerry.clean_arch_mvvm.base.usecase.UseCaseResult
import com.jerry.clean_arch_mvvm.base.utils.DisplayUtil
import io.mockk.coEvery
import io.mockk.junit5.MockKExtension
import io.mockk.mockkClass
import io.mockk.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher

import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
@MockKExtension.ConfirmVerification
class AssetsViewModelTest {

    private lateinit var getAssetsUseCase: GetAssetsUseCase
    private lateinit var assetMapper: AssetMapper
    private lateinit var displayUtil: DisplayUtil
    private lateinit var assetsViewModel: AssetsViewModel


    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        getAssetsUseCase = mockkClass(GetAssetsUseCase::class)
        assetMapper = AssetMapper()
        displayUtil = DisplayUtil()


        assetsViewModel = AssetsViewModel(
            getAssetsUseCase = getAssetsUseCase,
            assetMapper = assetMapper,
            displayUtil = displayUtil
        )

    }

    @AfterEach
    fun after(){
        Dispatchers.resetMain()
    }

    @Test
    fun `test assets view model ui state expected success state`() = runTest {
        val useCaseResult = UseCaseResult.Success(AssetsTestStubs.testAssetsResponseData)

        assetsViewModel = AssetsViewModel(
            getAssetsUseCase = getAssetsUseCase,
            assetMapper = assetMapper,
            displayUtil = displayUtil,
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
        Assertions.assertEquals(3, events.size)
        Assertions.assertEquals(UiState.Initial, events[0])
        Assertions.assertEquals(UiState.Loading, events[1])

    }


}