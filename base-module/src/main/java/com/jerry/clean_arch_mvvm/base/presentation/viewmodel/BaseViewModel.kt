package com.jerry.clean_arch_mvvm.base.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerry.clean_arch_mvvm.base.presentation.mvi.MyIntent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch


abstract class BaseViewModel<I: MyIntent>(
    open var dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    //from UI Intent Channel
    private val intentChannel = Channel<I>()

    fun sendIntent(intent: I) {
        viewModelScope.launch(dispatcher) {
            intentChannel.send(intent)
        }
    }

    private fun handleIntent() {
        viewModelScope.launch(dispatcher) {
            intentChannel.consumeAsFlow()
                .collect {
                    handleTracker(it)
                    handleIntent(it)
            }
        }
    }

    abstract fun handleIntent(intent: I)

    //which is send event to GA4 / tracking logging
    abstract fun handleTracker(intent: I)

    fun initIntent(){
        handleIntent()
    }

    override fun onCleared() {
        super.onCleared()
        intentChannel.cancel()
    }
}