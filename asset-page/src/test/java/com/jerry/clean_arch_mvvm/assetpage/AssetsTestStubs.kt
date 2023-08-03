package com.jerry.clean_arch_mvvm.assetpage

import com.jerry.clean_arch_mvvm.assetpage.domain.entities.api.AssetData
import com.jerry.clean_arch_mvvm.assetpage.domain.entities.api.AssetsResponseData

class AssetsTestStubs {

    companion object {

        const val testStr = ""
        const val errorMessage = "this is error"
        object FakeError: IllegalArgumentException()

        val testAssetData = AssetData(
            changePercent24Hr = null,
            explorer = null,
            id = null,
            marketCapUsd = null,
            maxSupply = null,
            name = null,
            priceUsd = null,
            rank = null,
            supply = null,
            symbol = null,
            volumeUsd24Hr = null,
            vwap24Hr = null
        )

        val testAssetsResponseData =
            AssetsResponseData (
                assetData = listOf(
                    testAssetData.copy(
                        id = "1",
                        symbol = "symbol 1",
                        name = "name 1",
                        priceUsd = "price 1"
                    ),
                    testAssetData.copy(
                        id = "2",
                        symbol = "symbol 2",
                        name = "name 2",
                        priceUsd = "price 2"
                    )
                ),
                error = null,
                timestamp = null
            )

    }
}