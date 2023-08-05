package com.jerry.clean_arch_mvvm.assetpage.domain.usecase


import com.jerry.clean_arch_mvvm.sharedtest.AssetsTestStubs.Companion.errorMessage
import com.jerry.clean_arch_mvvm.sharedtest.AssetsTestStubs.Companion.testAssetsResponseData
import com.jerry.clean_arch_mvvm.assetpage.data.mapper.AssetMapper
import com.jerry.clean_arch_mvvm.assetpage.domain.entities.ui.AssetUiItem
import com.jerry.clean_arch_mvvm.assetpage.domain.repository.AssetsRepository
import com.jerry.clean_arch_mvvm.base.usecase.UseCaseResult
import com.jerry.clean_arch_mvvm.base.utils.DisplayUtil
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
    private lateinit var assetMapper: AssetMapper
    private lateinit var displayUtil: DisplayUtil

    @BeforeEach
    fun setUp() {
        assetsRepository =  mockkClass(AssetsRepository::class)
        displayUtil = DisplayUtil()
        assetMapper = AssetMapper(displayUtil = displayUtil)
        getAssetsUseCase = GetAssetsUseCase(assetsRepository, assetMapper)
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
            (actual as UseCaseResult.Success<List<AssetUiItem>>).data.size,

            testAssetsResponseData.assetData!!.size
        )
    }

    @Test
    fun `test GetAssetsUseCase invoke() return customer error state`() = runTest {

        val result = testAssetsResponseData.copy(
            error = errorMessage
        )

        //assign
        coEvery { assetsRepository.getAssets() } returns result

        //action
        val actual = getAssetsUseCase()

        //verify
        assertTrue(actual is UseCaseResult.CustomerError)
        assertEquals(
            result.error,
            (actual as UseCaseResult.CustomerError).error
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