package com.jerry.clean_arch_mvvm.marketpage.presentation.viewmodel.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var onIntentPublishSubjectDisposable: Disposable? = null

    private val onIntentPublishSubject: PublishSubject<String> by lazy { PublishSubject.create() }

    private val _uiState = MutableStateFlow<String>("")
    val uiState = _uiState.asStateFlow()

    private fun observeOnIntentSubject(): Disposable {
        return onIntentPublishSubject
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                   doSomething(it)
                },
                {
                    //TODO
                }
            )
            .also { compositeDisposable.add(it) }
    }

    private fun doSomething(str: String){
        _uiState.value = str
        viewModelScope.launch {
            val result = mycase()
            _uiState.emit(result)
        }
    }

    fun initIntent(){
        onIntentPublishSubjectDisposable = observeOnIntentSubject()
    }

    fun sendIntent(intent: String) {
        onIntentPublishSubject.onNext(intent)
    }

    suspend fun mycase():String{
        return "from my case"
    }
}