package com.jerry.clean_arch_mvvm.assetpage.domain.usecase

import com.jerry.clean_arch_mvvm.assetpage.data.mapper.AssetMapper
import com.jerry.clean_arch_mvvm.assetpage.domain.entities.api.AssetsResponseData
import com.jerry.clean_arch_mvvm.assetpage.domain.entities.ui.AssetUiItem
import com.jerry.clean_arch_mvvm.assetpage.domain.repository.AssetsRepository
import com.jerry.clean_arch_mvvm.base.usecase.UseCaseResult
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

//we can implement business logic at here, for example filter data or sorting  ...etc
class GetAssetsUseCase @Inject constructor(
    private val assetsRepository: AssetsRepository,
    private val assetMapper: AssetMapper
) {

    suspend operator fun invoke() : UseCaseResult<List<AssetUiItem>> {
        return try {
            val response = assetsRepository.getAssets()
            if (response.error != null){
                UseCaseResult.CustomerError(response.error)
            } else {
                UseCaseResult.Success(
                    response.assetData?.map {
                        assetMapper.mapToUiData(it)
                    } ?: emptyList()
                )
            }
        } catch (e: Exception){
            UseCaseResult.Failure(e)
        }
    }

}