package com.jerry.clean_arch_mvvm.marketpage.domain.usecase

import com.jerry.clean_arch_mvvm.base.usecase.UseCaseResult
import com.jerry.clean_arch_mvvm.marketpage.data.mapper.MarketMapper
import com.jerry.clean_arch_mvvm.marketpage.domain.entities.ui.MarketUiItem
import com.jerry.clean_arch_mvvm.marketpage.domain.repository.MarketRepository
import com.jerry.clean_arch_mvvm.marketpage.exception.MarketNotFoundException
import com.yoti.android.cryptocurrencychallenge.domain.model.market.MarketData
import javax.inject.Inject

class GetMarketUseCase @Inject constructor(
    private val marketRepository: MarketRepository,
    private val mapper: MarketMapper
) {

    suspend operator fun invoke(baseId: String): UseCaseResult<MarketUiItem> {
        return try {
            val response = marketRepository.getMarket(baseId = baseId)
            if (response.error != null){
                UseCaseResult.CustomerError(response.error)
            } else {
                val marketUiItem =
                    response.marketData
                        ?.maxByOrNull {
                            it.volumeUsd24Hr?.toDoubleOrNull() ?: 0.0
                        }?.let {
                            mapper.mapToUiData(it)
                        }
                if (marketUiItem != null) {
                    UseCaseResult.Success(marketUiItem)
                } else {
                    UseCaseResult.Failure(MarketNotFoundException(""))
                }
            }
        } catch (e: Exception) {
            UseCaseResult.Failure(e)
        }
    }

}
