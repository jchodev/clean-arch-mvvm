package com.jerry.clean_arch_mvvm.marketpage.presentation.mvi

import com.jerry.clean_arch_mvvm.base.presentation.mvi.MyIntent

sealed class MarketIntent: MyIntent {
    class Initial(val baseId: String): MarketIntent()
}
