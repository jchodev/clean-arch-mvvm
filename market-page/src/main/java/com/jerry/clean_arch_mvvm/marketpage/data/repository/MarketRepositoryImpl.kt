package com.jerry.clean_arch_mvvm.marketpage.data.repository

import com.jerry.clean_arch_mvvm.marketpage.domain.repository.MarketRepository
import com.jerry.clean_arch_mvvm.marketpage.network.MarketServiceApi
import com.yoti.android.cryptocurrencychallenge.domain.model.market.MarketResponseData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class MarketRepositoryImpl @Inject constructor(
    private val marketServiceApi: MarketServiceApi,
    @Named("Dispatchers.IO")
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): MarketRepository {

    override suspend fun getMarket(baseId: String): MarketResponseData = withContext(ioDispatcher){
        marketServiceApi.getMarkets(baseId = baseId)
    }

}