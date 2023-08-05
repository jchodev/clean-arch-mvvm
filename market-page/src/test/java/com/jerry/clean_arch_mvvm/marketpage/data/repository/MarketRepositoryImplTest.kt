package com.jerry.clean_arch_mvvm.marketpage.data.repository

import com.jerry.clean_arch_mvvm.sharedtest.MarketTestStubs
import com.jerry.clean_arch_mvvm.sharedtest.MarketTestStubs.Companion.errorMessage
import com.jerry.clean_arch_mvvm.marketpage.network.MarketServiceApi
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
class MarketRepositoryImplTest {

    private lateinit var marketServiceApi: MarketServiceApi
    private lateinit var marketRepositoryImpl: MarketRepositoryImpl

    @BeforeEach
    fun setUp() {
        marketServiceApi =  mockkClass(MarketServiceApi::class)
        marketRepositoryImpl = MarketRepositoryImpl(
            marketServiceApi = marketServiceApi
        )
    }

    @Test
    fun `test MarketRepositoryImpl getMarket() success`() {
        val successResponse = MarketTestStubs.testMarketResponseData
        runTest {


            //assign
            coEvery { marketServiceApi.getMarkets(any()) } returns successResponse

            //action
            val actual = marketRepositoryImpl.getMarket("")

            //verify
            Assertions.assertEquals(successResponse.error, actual.error)
            Assertions.assertTrue(actual.marketData != null)
            Assertions.assertEquals(successResponse.marketData!!.size, actual.marketData!!.size)
            Assertions.assertEquals(successResponse.marketData!![0], actual.marketData!![0])
        }
    }

    @Test
    fun `test MarketRepositoryImpl getMarket() throws exception`() {
        runTest {

            //assign
            coEvery { marketServiceApi.getMarkets(any()) } throws MarketTestStubs.Companion.FakeError

            //action
            var exceptionThrown = false
            try {
                marketRepositoryImpl.getMarket("")
            } catch (exception: MarketTestStubs.Companion.FakeError) {
                exceptionThrown = true
            }

            //verify
            Assertions.assertTrue(
                exceptionThrown
            )
        }
    }

    @Test
    fun `test MarketRepositoryImpl getMarket() return customer error`() {
        val response = MarketTestStubs.testMarketResponseData.copy(
            marketData = null,
            error = errorMessage
        )
        runTest {

            //assign
            coEvery { marketServiceApi.getMarkets(any()) } returns response

            //action
            val actual = marketRepositoryImpl.getMarket("")

            //verify
            Assertions.assertEquals(response.error, actual.error)
        }

    }
}