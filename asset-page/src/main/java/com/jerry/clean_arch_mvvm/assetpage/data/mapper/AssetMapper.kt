package com.jerry.clean_arch_mvvm.assetpage.data.mapper

import com.jerry.clean_arch_mvvm.assetpage.domain.entities.api.AssetData
import com.jerry.clean_arch_mvvm.assetpage.domain.entities.ui.AssetUiItem
import com.jerry.clean_arch_mvvm.base.utils.DisplayUtil

//it is used at view model
class AssetMapper {

    fun mapToUiData(displayUtil: DisplayUtil, assetData: AssetData): AssetUiItem {
        return AssetUiItem(
            id = assetData.id,
            name = displayUtil.displayNormalText(assetData.name),
            price = displayUtil.displayNormalText(assetData.priceUsd),
            symbol = displayUtil.displayNormalText(assetData.symbol)
        )
    }

}