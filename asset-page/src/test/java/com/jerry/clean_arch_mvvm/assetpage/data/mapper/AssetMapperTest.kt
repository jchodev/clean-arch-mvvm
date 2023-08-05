package com.jerry.clean_arch_mvvm.assetpage.data.mapper

import com.jerry.clean_arch_mvvm.sharedtest.AssetsTestStubs
import com.jerry.clean_arch_mvvm.base.utils.DisplayUtil
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
@MockKExtension.ConfirmVerification
class AssetMapperTest {

    private lateinit var assetMapper: AssetMapper
    private lateinit var displayUtil: DisplayUtil

    @BeforeEach
    fun setup() {
        displayUtil = DisplayUtil()
        assetMapper = AssetMapper(displayUtil)
    }

    @Test
    fun `test AssetMapperTest match domain data to ui with expected`()  {

        val testAssetData = AssetsTestStubs.testAssetData.copy(
            id = "1",
            name = "name 1",
            priceUsd = "price 1",
            symbol = "symbol 1"
        )

        //action
        val actual = assetMapper.mapToUiData(
            assetData = testAssetData
        )

        //verify
        Assertions.assertEquals(
            testAssetData.id,
            actual.id
        )
        Assertions.assertEquals(
            testAssetData.name,
            actual.name
        )
        Assertions.assertEquals(
            testAssetData.priceUsd,
            actual.price
        )
        Assertions.assertEquals(
            testAssetData.symbol,
            actual.symbol
        )

    }
}