package com.jerry.clean_arch_mvvm.marketpage.presentation.viewmodel.mvi

import android.util.Log

import androidx.lifecycle.viewModelScope
import com.jerry.clean_arch_mvvm.base.presentation.UiState
import com.jerry.clean_arch_mvvm.base.presentation.viewmodel.BaseRxMVIViewModel
import com.jerry.clean_arch_mvvm.base.usecase.UseCaseResult
import com.jerry.clean_arch_mvvm.marketpage.domain.entities.ui.MarketUiItem
import com.jerry.clean_arch_mvvm.marketpage.domain.usecase.GetMarketUseCase
import com.jerry.clean_arch_mvvm.marketpage.presentation.mvi.MarketAction
import com.jerry.clean_arch_mvvm.marketpage.presentation.mvi.MarketIntent
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
    @Named("Dispatchers.Main") override var dispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val getMarketUseCase: GetMarketUseCase
): BaseRxMVIViewModel<MarketIntent, MarketAction>(dispatcher) {

    private val TAG = "MarketViewModel"

    //------------------------

    private val _uiState = MutableStateFlow<UiState<MarketUiItem>>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    private fun getMarketsByBaseId(baseId : String){
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
    //-------------------------

    override fun handleIntent(intent: MarketIntent): MarketAction {
        Log.d(TAG, "handleIntent::${intent}")

        return when (intent){
            is MarketIntent.Initial -> MarketAction.GetMarketByBaseId(intent.baseId)
        }
    }

    override fun handleIntentTracker(intent: MarketIntent) {
        Log.d(TAG, "handleIntentTracker::${intent}")
    }

    override fun handleActionTracker(action: MarketAction) {
        Log.d(TAG, "handleActionTracker::${action}")
    }

    override fun handleAction(action: MarketAction) {
        when (action){
            is MarketAction.GetMarketByBaseId -> getMarketsByBaseId(action.baseId)
        }
    }

}