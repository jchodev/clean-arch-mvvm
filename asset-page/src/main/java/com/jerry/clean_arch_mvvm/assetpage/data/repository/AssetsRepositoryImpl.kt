package com.jerry.clean_arch_mvvm.assetpage.data.repository

import com.jerry.clean_arch_mvvm.assetpage.domain.entities.api.AssetsResponseData
import com.jerry.clean_arch_mvvm.assetpage.domain.repository.AssetsRepository
import com.jerry.clean_arch_mvvm.assetpage.network.AssetServiceApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class AssetsRepositoryImpl @Inject constructor(
    private val assetServiceApi: AssetServiceApi
)  : AssetsRepository {

    override suspend fun getAssets(): AssetsResponseData {
        return assetServiceApi.getAssets()
    }
}