package com.jerry.clean_arch_mvvm.assetpage.presentation.viewmodel.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.jerry.clean_arch_mvvm.assetpage.domain.entities.ui.AssetUiItem
import com.jerry.clean_arch_mvvm.assetpage.domain.usecase.GetAssetsUseCase
import com.jerry.clean_arch_mvvm.base.presentation.UiState
import com.jerry.clean_arch_mvvm.base.usecase.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AssetsViewModel @Inject constructor(
    private val getAssetsUseCase: GetAssetsUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<AssetUiItem>>>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    fun getAssetList(){
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
            }
        }
    }
}