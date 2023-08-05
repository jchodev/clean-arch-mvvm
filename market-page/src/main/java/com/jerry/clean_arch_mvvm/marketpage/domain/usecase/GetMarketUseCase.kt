package com.jerry.clean_arch_mvvm.marketpage.domain.usecase

import com.jerry.clean_arch_mvvm.base.usecase.UseCaseResult
import com.jerry.clean_arch_mvvm.marketpage.data.mapper.MarketMapper
import com.jerry.clean_arch_mvvm.marketpage.domain.entities.ui.MarketUiItem
import com.jerry.clean_arch_mvvm.marketpage.domain.repository.MarketRepository
import com.jerry.clean_arch_mvvm.marketpage.exception.MarketNotFoundException
import com.yoti.android.cryptocurrencychallenge.domain.model.market.MarketData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class GetMarketUseCase @Inject constructor(
    private val marketRepository: MarketRepository,
    private val mapper: MarketMapper,
    @Named("Dispatchers.IO")
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(baseId: String): UseCaseResult<MarketUiItem> = withContext(ioDispatcher){
        try {
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
