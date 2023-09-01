package com.jerry.clean_arch_mvvm.marketpage.data.mapper

import com.jerry.clean_arch_mvvm.base.utils.DisplayUtil
import com.jerry.clean_arch_mvvm.marketpage.domain.entities.api.MarketData
import com.jerry.clean_arch_mvvm.marketpage.domain.entities.ui.MarketUiItem
import javax.inject.Inject

class MarketMapper @Inject constructor(
    private val displayUtil: DisplayUtil
) {

    fun mapToUiData(marketData: MarketData): MarketUiItem {
        return MarketUiItem(
            exchangeId = displayUtil.displayNormalText(marketData.exchangeId),
            rank = displayUtil.displayNormalText(marketData.rank),
            updated = if (marketData.updated != null) {
                displayUtil.displayDateFormat(marketData.updated)
            } else {
                displayUtil.displayNormalText(null)
            },
            price = displayUtil.displayNormalText(marketData.priceUsd),
        )
    }
}