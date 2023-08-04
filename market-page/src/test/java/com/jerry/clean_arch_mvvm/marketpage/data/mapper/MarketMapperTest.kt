package com.jerry.clean_arch_mvvm.marketpage.data.mapper

import com.jerry.clean_arch_mvvm.base.utils.DisplayUtil
import com.jerry.clean_arch_mvvm.marketpage.MarketTestStubs.Companion.testMarketData
import com.jerry.clean_arch_mvvm.marketpage.MarketTestStubs.Companion.testMarketResponseData
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


@ExperimentalCoroutinesApi
@MockKExtension.ConfirmVerification
class MarketMapperTest {

    private lateinit var mapper: MarketMapper
    private lateinit var displayUtil: DisplayUtil

    @BeforeEach
    fun setup() {
        displayUtil = DisplayUtil()
        mapper = MarketMapper(displayUtil)
    }

    @Test
    fun `test MarketMapper match domain data to ui with expected`()  {

        val testData = testMarketResponseData.marketData?.get(0)

        //action
        val actual = mapper.mapToUiData(
            testData!!
        )

        //verify
        Assertions.assertEquals(
            testData.exchangeId,
            actual.exchangeId
        )
        Assertions.assertEquals(
            testData.rank,
            actual.rank
        )
        Assertions.assertEquals(
            testData.priceUsd,
            actual.price
        )
        Assertions.assertEquals(
            "---",
            actual.updated
        )
    }
}