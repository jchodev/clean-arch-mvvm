package com.jerry.clean_arch_mvvm.assetpage.presentation.mvi

import com.jerry.clean_arch_mvvm.base.presentation.mvi.MviIntent

sealed class AssetsIntent: MviIntent {
    object Initial: AssetsIntent()
}


