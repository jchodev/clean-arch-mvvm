package com.jerry.clean_arch_mvvm.marketpage.domain.repository

import com.jerry.clean_arch_mvvm.marketpage.domain.entities.api.MarketResponseData

interface MarketRepository {
    suspend fun getMarket(baseId: String): MarketResponseData
}

