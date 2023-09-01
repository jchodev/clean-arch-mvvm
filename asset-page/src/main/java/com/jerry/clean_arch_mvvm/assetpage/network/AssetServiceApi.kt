package com.jerry.clean_arch_mvvm.assetpage.network

import com.jerry.clean_arch_mvvm.assetpage.domain.entities.api.AssetsResponseData
import retrofit2.http.GET

interface AssetServiceApi {

    @GET("/v2/assets")
    suspend fun getAssets(): AssetsResponseData

}