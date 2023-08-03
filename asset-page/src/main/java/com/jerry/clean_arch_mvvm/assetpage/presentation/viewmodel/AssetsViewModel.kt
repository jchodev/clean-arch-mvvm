package com.jerry.clean_arch_mvvm.assetpage.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerry.clean_arch_mvvm.assetpage.data.mapper.AssetMapper
import com.jerry.clean_arch_mvvm.assetpage.domain.entities.ui.AssetUiItem
import com.jerry.clean_arch_mvvm.assetpage.domain.usecase.GetAssetsUseCase
import com.jerry.clean_arch_mvvm.base.presentation.UiState
import com.jerry.clean_arch_mvvm.base.usecase.UseCaseResult
import com.jerry.clean_arch_mvvm.base.utils.DisplayUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class AssetsViewModel @Inject constructor(
    //we assign the dispatcher at here, BECAUSE for junit testing
    //https://developer.android.com/kotlin/coroutines/test
    @Named("Dispatchers.Main")
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val getAssetsUseCase: GetAssetsUseCase,
    private val assetMapper: AssetMapper,
    private val displayUtil: DisplayUtil
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<AssetUiItem>>>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    fun getAssetList(){
        viewModelScope.launch(dispatcher) {
            _uiState.value = UiState.Loading
            when (val result = getAssetsUseCase()) {
                is UseCaseResult.Failure -> {
                    _uiState.value = UiState.Failure(result.throwable)
                }
                is UseCaseResult.Success -> {
                    if (result.data.error != null) {
                        _uiState.value = UiState.CustomerError(
                            result.data.error!!
                        )
                    } else {
                        _uiState.value = UiState.Success(
                            result.data
                                .assetData?.map {
                                    assetMapper.mapToUiData(displayUtil, it)
                                } ?: emptyList()
                        )
                    }
                }
            }
        }
    }
}