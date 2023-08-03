package com.jerry.clean_arch_mvvm.assetpage.domain.repository

import com.jerry.clean_arch_mvvm.assetpage.domain.entities.api.AssetsResponseData

interface AssetsRepository {

    suspend fun getAssets(): AssetsResponseData

}