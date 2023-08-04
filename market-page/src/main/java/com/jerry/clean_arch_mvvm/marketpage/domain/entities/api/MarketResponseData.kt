package com.yoti.android.cryptocurrencychallenge.domain.model.market


import com.google.gson.annotations.SerializedName

data class MarketResponseData(
    @SerializedName("data")
    val marketData: List<MarketData>?,

    @SerializedName("error")
    val error: String?,

    @SerializedName("timestamp")
    val timestamp: Long?
)