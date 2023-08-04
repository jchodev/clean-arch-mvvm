package com.jerry.clean_arch_mvvm.marketpage

import com.jerry.clean_arch_mvvm.marketpage.domain.entities.ui.MarketUiItem
import com.yoti.android.cryptocurrencychallenge.domain.model.market.MarketData
import com.yoti.android.cryptocurrencychallenge.domain.model.market.MarketResponseData

class MarketTestStubs {
    companion object {
        const val testStr = ""
        const val errorMessage = "this is error"
        object FakeError: IllegalArgumentException()

        val testMarketData = MarketData(
            baseId = null,
            baseSymbol = null,
            exchangeId = null,
            percentExchangeVolume = null,
            priceQuote = null,
            priceUsd = null,
            quoteId = null,
            quoteSymbol = null,
            rank = null,
            tradesCount24Hr = null,
            updated = null,
            volumeUsd24Hr = null
        )

        val testMarketResponseData = MarketResponseData(
            marketData = listOf(
                testMarketData.copy(
                    exchangeId = "exchange Id 1",
                    rank = "rank1",
                    volumeUsd24Hr = "1",
                    priceUsd = "price 1"
                ),
                testMarketData.copy(
                    exchangeId = "exchange Id 2",
                    rank = "rank 2",
                    volumeUsd24Hr = "2",
                    priceUsd = "price 2"
                )
            ),
            error = null,
            timestamp = null
        )

        val testMarketUiItem = MarketUiItem(
            exchangeId = "exchange id 1",
            rank = "rank 1",
            price = "price 1",
            updated = "updated 1"
        )
    }


}