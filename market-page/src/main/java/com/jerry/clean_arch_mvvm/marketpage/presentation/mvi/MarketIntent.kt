package com.jerry.clean_arch_mvvm.marketpage.presentation.mvi

import com.jerry.clean_arch_mvvm.base.presentation.mvi.MviIntent

sealed class MarketIntent: MviIntent {
    class Initial(val baseId: String): MarketIntent()
}
