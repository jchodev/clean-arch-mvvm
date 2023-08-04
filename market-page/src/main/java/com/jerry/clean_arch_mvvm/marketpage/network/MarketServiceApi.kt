package com.jerry.clean_arch_mvvm.marketpage.network

import com.yoti.android.cryptocurrencychallenge.domain.model.market.MarketResponseData
import retrofit2.http.GET
import retrofit2.http.Query

interface MarketServiceApi {

    @GET("/v2/markets")
    suspend fun getMarkets(@Query("baseId") baseId: String): MarketResponseData
}