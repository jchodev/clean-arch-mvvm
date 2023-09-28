package com.jerry.clean_arch_mvvm.assetpage.presentation.mvi

import com.jerry.clean_arch_mvvm.base.presentation.mvi.MviAction

sealed class AssetsAction: MviAction {
    object GetAssetsList: AssetsAction()
}
