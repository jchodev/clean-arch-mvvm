package com.jerry.clean_arch_mvvm.assetpage.presentation.viewmodel.mvi


import androidx.lifecycle.viewModelScope

import com.jerry.clean_arch_mvvm.assetpage.domain.entities.ui.AssetUiItem
import com.jerry.clean_arch_mvvm.assetpage.domain.usecase.GetAssetsUseCase
import com.jerry.clean_arch_mvvm.assetpage.presentation.mvi.AssetsAction
import com.jerry.clean_arch_mvvm.assetpage.presentation.mvi.AssetsIntent
import com.jerry.clean_arch_mvvm.base.presentation.UiState
import com.jerry.clean_arch_mvvm.base.usecase.UseCaseResult
import com.jerry.clean_arch_mvvm.base.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssetsViewModel @Inject constructor(
    private val getAssetsUseCase: GetAssetsUseCase,
): BaseViewModel<AssetsIntent, AssetsAction>() {

    private val TAG = "AssetsViewModel"

    private val _uiState = MutableStateFlow<UiState<List<AssetUiItem>>>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    private fun getAssetList(){

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            when (val result = getAssetsUseCase.invoke()) {
                is UseCaseResult.Failure -> {
                    _uiState.value = UiState.Failure(result.throwable)
                }
                is UseCaseResult.CustomerError -> {
                    _uiState.value = UiState.CustomerError(
                        result.error
                    )
                }
                is UseCaseResult.Success -> {
                    _uiState.value = UiState.Success(
                        result.data
                    )
                }

                else -> {}
            }
        }
    }

    override fun handleIntentTracker(intent: AssetsIntent) {
        //TODO("Not yet implemented")
    }

    override fun handleActionTracker(action: AssetsAction) {
        //TODO("Not yet implemented")
    }

    override fun handleIntent(intent: AssetsIntent): AssetsAction {
        return when (intent){
            is AssetsIntent.Initial -> AssetsAction.GetAssetsList
        }
    }

    override fun handleAction(action: AssetsAction) {
        when (action){
            is AssetsAction.GetAssetsList -> getAssetList()
        }
    }
}