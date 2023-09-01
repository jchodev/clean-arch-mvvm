package com.jerry.clean_arch_mvvm.assetpage.data.repository


import com.jerry.clean_arch_mvvm.sharedtest.AssetsTestStubs
import com.jerry.clean_arch_mvvm.assetpage.network.AssetServiceApi
import io.mockk.coEvery
import io.mockk.junit5.MockKExtension
import io.mockk.mockkClass
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


@ExperimentalCoroutinesApi
@MockKExtension.ConfirmVerification
class AssetsRepositoryImplTest {

    private lateinit var assetServiceApi: AssetServiceApi
    private lateinit var assetsRepositoryImpl: AssetsRepositoryImpl

    @BeforeEach
    fun setUp() {
        assetServiceApi =  mockkClass(AssetServiceApi::class)
    }

    @Test
    fun `test AssetsRepositoryImpl getAssets() success`() {
        val assetsSuccessResponse = AssetsTestStubs.testAssetsResponseData
        runTest {
            assetsRepositoryImpl = AssetsRepositoryImpl(
                assetServiceApi =  assetServiceApi
            )
            //assign
            coEvery { assetServiceApi.getAssets() } returns assetsSuccessResponse

            //action
            val actual = assetsRepositoryImpl.getAssets()

            //verify
            Assertions.assertEquals(assetsSuccessResponse.error, actual.error)
            Assertions.assertEquals(true, actual.assetData != null)
            Assertions.assertEquals(assetsSuccessResponse.assetData!!.size, actual.assetData!!.size)
            Assertions.assertEquals(assetsSuccessResponse.assetData!![0].id, actual.assetData!![0].id)
            Assertions.assertEquals(assetsSuccessResponse.assetData!![0].name, actual.assetData!![0].name)
            Assertions.assertEquals(assetsSuccessResponse.assetData!![0].symbol, actual.assetData!![0].symbol)
            Assertions.assertEquals(assetsSuccessResponse.assetData!![0].priceUsd, actual.assetData!![0].priceUsd)
        }
    }

    @Test
    fun `test AssetsRepositoryImpl getAssets() throws exception`() {
        runTest {

            assetsRepositoryImpl = AssetsRepositoryImpl(
                assetServiceApi =  assetServiceApi
            )

            //assign
            coEvery { assetServiceApi.getAssets() } throws AssetsTestStubs.Companion.FakeError

            //action
            var exceptionThrown = false
            try {
                //action
                assetsRepositoryImpl.getAssets()
            } catch (exception: AssetsTestStubs.Companion.FakeError) {
                exceptionThrown = true
            }


            //verify
            Assertions.assertTrue(
                exceptionThrown
            )
        }
    }

    @Test
    fun `test AssetsRepositoryImpl getAssets() return customer error`() {
        val response = AssetsTestStubs.testAssetsResponseData.copy(
            assetData = null,
            error = AssetsTestStubs.errorMessage
        )
        runTest {

            assetsRepositoryImpl = AssetsRepositoryImpl(
                assetServiceApi =  assetServiceApi
            )

            //assign
            coEvery { assetServiceApi.getAssets() } returns response
            //action
            val actual = assetsRepositoryImpl.getAssets()

            //verify
            Assertions.assertEquals(response.error, actual.error)
        }
    }

}