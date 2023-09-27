package com.jerry.clean_arch_mvvm.assetpage.presentation.mvi

import com.jerry.clean_arch_mvvm.base.presentation.mvi.MyIntent

sealed class AssetsIntent: MyIntent {
    object Initial: AssetsIntent()
}


