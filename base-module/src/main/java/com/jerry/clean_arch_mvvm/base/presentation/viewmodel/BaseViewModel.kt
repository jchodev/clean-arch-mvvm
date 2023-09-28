package com.jerry.clean_arch_mvvm.base.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerry.clean_arch_mvvm.base.presentation.mvi.MviAction
import com.jerry.clean_arch_mvvm.base.presentation.mvi.MviIntent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

//this is a base class which is used for MVI with flow channel
abstract class BaseViewModel<INTENT: MviIntent, ACTION: MviAction>(
    open var dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val TAG : String = "BaseViewModel"

    //from UI Intent Channel
    private val intentChannel = Channel<INTENT>()

    fun sendIntent(intent: INTENT) {
        viewModelScope.launch(dispatcher) {
            intentChannel.send(intent)
        }
    }

    //base on intent type to fire action
    protected abstract fun handleIntent(intent: INTENT) : ACTION
    protected abstract fun handleAction(action: ACTION)

    //which is send event to GA4 / tracking logging
    protected abstract fun handleIntentTracker(intent: INTENT)

    //which is send event to GA4 / tracking logging
    protected abstract fun handleActionTracker(action: ACTION)

    fun initIntent(){
        viewModelScope.launch(dispatcher) {
            intentChannel.consumeAsFlow()
                .onStart {
                    Log.d("BaseViewModel", "onStart")
                }
                .collect {
                    Log.d(TAG ,"intent :: ${it}")
                    handleIntentTracker(it)
                    val action = handleIntent(it)
                    Log.d(TAG ,"action :: ${action}")
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