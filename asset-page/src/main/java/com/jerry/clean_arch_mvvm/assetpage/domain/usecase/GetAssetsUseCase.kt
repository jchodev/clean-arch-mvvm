package com.jerry.clean_arch_mvvm.assetpage.domain.usecase

import com.jerry.clean_arch_mvvm.assetpage.data.mapper.AssetMapper
import com.jerry.clean_arch_mvvm.assetpage.domain.entities.api.AssetsResponseData
import com.jerry.clean_arch_mvvm.assetpage.domain.entities.ui.AssetUiItem
import com.jerry.clean_arch_mvvm.assetpage.domain.repository.AssetsRepository
import com.jerry.clean_arch_mvvm.base.usecase.UseCaseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

//we can implement business logic at here, for example filter data or sorting  ...etc
class GetAssetsUseCase @Inject constructor(
    private val assetsRepository: AssetsRepository,
    private val assetMapper: AssetMapper,
    @Named("Dispatchers.IO")
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke() : UseCaseResult<List<AssetUiItem>> = withContext(ioDispatcher) {
        try {
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