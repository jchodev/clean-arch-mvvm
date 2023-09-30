package com.jerry.clean_arch_mvvm.marketpage.presentation.viewmodel.mvi

import android.util.Log
import app.cash.turbine.test
import com.jerry.clean_arch_mvvm.marketpage.domain.usecase.GetMarketUseCase
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MyViewModelTest {

    private lateinit var getMarketUseCase: GetMarketUseCase
    private lateinit var myViewModel: MyViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        getMarketUseCase = mockkClass(GetMarketUseCase::class)

        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0

        myViewModel = MyViewModel()
        myViewModel.initIntent()
    }

    @AfterEach
    fun after() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `baba`() = runTest {

        // Arrange
        val expectedValue = "apple"

        // Act
        val job = launch {
            myViewModel.uiState.test {
                println(awaitItem())
                println(awaitItem())
            }
        }

        myViewModel.sendIntent("apple")

        // Assert
        job.join()
    }

    @Test
    fun `bbb`() = runTest {

        // Arrange
        val expectedValue = "apple"

        // Act
        val job = launch {
            myViewModel.uiState.test {
                println(awaitItem())
                println(awaitItem())
            }
        }

        myViewModel.sendIntent("apple")

        // Assert
        job.join()
    }

}