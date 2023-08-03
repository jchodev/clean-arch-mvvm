package com.jerry.clean_arch_mvvm.assetpage.domain.entities.api

import com.google.gson.annotations.SerializedName

//data from assets api
data class AssetsResponseData(
    @SerializedName("data")
    val assetData: List<AssetData>?,

    @SerializedName("error")
    val error: String?,

    @SerializedName("timestamp")
    val timestamp: Long?
)