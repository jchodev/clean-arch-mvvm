package com.jerry.clean_arch_mvvm.assetpage.domain.usecase

import com.jerry.clean_arch_mvvm.assetpage.AssetsTestStubs.Companion.testAssetsResponseData
import com.jerry.clean_arch_mvvm.assetpage.domain.entities.api.AssetsResponseData
import com.jerry.clean_arch_mvvm.assetpage.domain.repository.AssetsRepository
import com.jerry.clean_arch_mvvm.base.usecase.UseCaseResult
import io.mockk.coEvery
import io.mockk.junit5.MockKExtension
import io.mockk.mockkClass
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.net.SocketTimeoutException


@ExperimentalCoroutinesApi
@MockKExtension.ConfirmVerification
class GetAssetsUseCaseTest {

    private lateinit var assetsRepository: AssetsRepository
    private lateinit var getAssetsUseCase: GetAssetsUseCase

    @BeforeEach
    fun setUp() {
       assetsRepository =  mockkClass(AssetsRepository::class)
       getAssetsUseCase = GetAssetsUseCase(assetsRepository)
    }

    @Test
    fun `test GetAssetsUseCase invoke() return success state`() = runTest {

        //assign
        coEvery { assetsRepository.getAssets() } returns testAssetsResponseData

        //action
        val actual = getAssetsUseCase()

        //verify
        assertTrue(actual is UseCaseResult.Success)
        assertEquals(
            (actual as UseCaseResult.Success<AssetsResponseData>).data.assetData!!.size,
            testAssetsResponseData.assetData!!.size
        )
        assertEquals(
            (actual).data.error,
            null
        )
    }

    @Test
    fun `test GetAssetsUseCase invoke() return Failure status`() = runTest {
        //assign
        coEvery { assetsRepository.getAssets() }.throws(SocketTimeoutException())

        //action
        val actual = getAssetsUseCase()

        //verify
        assertTrue(actual is UseCaseResult.Failure)
        assertTrue(
            (actual as UseCaseResult.Failure).throwable is SocketTimeoutException
        )
    }
}