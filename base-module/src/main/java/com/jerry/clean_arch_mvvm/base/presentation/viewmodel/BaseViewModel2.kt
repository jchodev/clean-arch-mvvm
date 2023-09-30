package com.jerry.clean_arch_mvvm.base.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerry.clean_arch_mvvm.base.presentation.mvi.MviAction
import com.jerry.clean_arch_mvvm.base.presentation.mvi.MviIntent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

abstract class BaseViewModel2<INTENT: MviIntent, ACTION: MviAction>(): ViewModel(){
    private val TAG : String = "BaseViewModel2"

    //from UI Intent Channel
    private val intentChannel = Channel<INTENT>()

    fun sendIntent(intent: INTENT) {
        viewModelScope.launch  {
            Log.d(TAG, "intent :: ${intent}")
            handleIntentTracker(intent)
            val action = handleIntent(intent)
            Log.d(TAG, "action :: ${action}")
            handleActionTracker(action)
            handleAction(action)
        }
    }

    //base on intent type to fire action
    protected abstract suspend fun handleIntent(intent: INTENT) : ACTION
    protected abstract suspend fun handleAction(action: ACTION)

    //which is send event to GA4 / tracking logging
    protected abstract suspend fun handleIntentTracker(intent: INTENT)

    //which is send event to GA4 / tracking logging
    protected abstract suspend fun handleActionTracker(action: ACTION)

    fun initIntent(){
        viewModelScope.launch {
            intentChannel.consumeAsFlow()
                .onStart {
                    Log.d("BaseViewModel", "onStart")
                }
                .collect {
                    Log.d(TAG, "intent :: ${it}")
                    handleIntentTracker(it)
                    val action = handleIntent(it)
                    Log.d(TAG, "action :: ${action}")
                    handleActionTracker(action)
                    handleAction(action)
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        intentChannel.cancel()
    }
}