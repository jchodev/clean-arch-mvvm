package com.jerry.clean_arch_mvvm.marketpage.presentation.mvi

import com.jerry.clean_arch_mvvm.base.presentation.mvi.MviAction

sealed class MarketAction: MviAction {
    class GetMarketByBaseId(val baseId: String): MarketAction()
}
