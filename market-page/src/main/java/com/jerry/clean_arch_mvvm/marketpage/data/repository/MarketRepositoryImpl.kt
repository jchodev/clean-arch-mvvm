package com.jerry.clean_arch_mvvm.marketpage.data.repository

import com.jerry.clean_arch_mvvm.marketpage.domain.entities.api.MarketResponseData
import com.jerry.clean_arch_mvvm.marketpage.domain.repository.MarketRepository
import com.jerry.clean_arch_mvvm.marketpage.network.MarketServiceApi
import javax.inject.Inject

class MarketRepositoryImpl @Inject constructor(
    private val marketServiceApi: MarketServiceApi
): MarketRepository {

    override suspend fun getMarket(baseId: String): MarketResponseData {
        return marketServiceApi.getMarkets(baseId = baseId)
    }

}