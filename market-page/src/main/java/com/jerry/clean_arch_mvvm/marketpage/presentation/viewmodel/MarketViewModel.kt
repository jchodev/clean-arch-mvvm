package com.jerry.clean_arch_mvvm.marketpage.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerry.clean_arch_mvvm.base.presentation.UiState
import com.jerry.clean_arch_mvvm.base.usecase.UseCaseResult
import com.jerry.clean_arch_mvvm.marketpage.domain.entities.ui.MarketUiItem
import com.jerry.clean_arch_mvvm.marketpage.domain.usecase.GetMarketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MarketViewModel @Inject constructor(
    //we assign the dispatcher at here, BECAUSE for junit testing
    //https://developer.android.com/kotlin/coroutines/test
    @Named("Dispatchers.Main")
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val getMarketUseCase: GetMarketUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<MarketUiItem>>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    fun getMarketsByBaseId(baseId : String){
        viewModelScope.launch(dispatcher) {
            _uiState.value = UiState.Loading
            when (val result = getMarketUseCase(baseId = baseId)) {
                is UseCaseResult.Failure -> {
                    _uiState.value = UiState.Failure(result.throwable)
                }
                is UseCaseResult.Success -> {
                    _uiState.value = UiState.Success(result.data)
                }
                is UseCaseResult.CustomerError -> {
                    _uiState.value = UiState.CustomerError(
                        result.error
                    )
                }
            }
        }
    }
}