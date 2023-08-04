package com.jerry.clean_arch_mvvm.marketpage.domain.repository

import com.yoti.android.cryptocurrencychallenge.domain.model.market.MarketResponseData

interface MarketRepository {
    suspend fun getMarket(baseId: String): MarketResponseData
}

