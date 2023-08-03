package com.jerry.clean_arch_mvvm.assetpage.domain.usecase

import com.jerry.clean_arch_mvvm.assetpage.domain.entities.api.AssetsResponseData
import com.jerry.clean_arch_mvvm.assetpage.domain.repository.AssetsRepository
import com.jerry.clean_arch_mvvm.base.usecase.UseCaseResult
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

//we can implement business logic at here, for example filter data or sorting  ...etc
class GetAssetsUseCase @Inject constructor(private val assetsRepository: AssetsRepository) {

    suspend operator fun invoke() : UseCaseResult<AssetsResponseData> {
        return try {
            UseCaseResult.Success(assetsRepository.getAssets())
        } catch (e: Exception) {
            UseCaseResult.Failure(e)
        }
    }

}