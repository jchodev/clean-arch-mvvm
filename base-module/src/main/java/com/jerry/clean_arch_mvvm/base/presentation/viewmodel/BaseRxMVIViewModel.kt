package com.jerry.clean_arch_mvvm.base.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel

import com.jerry.clean_arch_mvvm.base.presentation.mvi.MyIntent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onStart

//this is a base class which is used for MVI with RxJava PublishSubject
abstract class BaseRxMVIViewModel<I: MyIntent>(
    open var dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var onIntentPublishSubjectDisposable: Disposable? = null

    private val onIntentPublishSubject: PublishSubject<I> by lazy { PublishSubject.create() }

    fun sendIntent(intent: I) {
        onIntentPublishSubject.onNext(intent)
    }

    private fun handleIntent() {
        onIntentPublishSubjectDisposable = observeOnIntentSubject()
    }

    private fun observeOnIntentSubject(): Disposable {
        return onIntentPublishSubject
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                Log.d("BaseRxMVIViewModel", "doOnSubscribe")
            }
            .doOnNext {
                    //aaa-> println("aaa1: ${aaa}")
                    handleTracker(it)
                    handleIntent(it)
            }
            .subscribe(
                {
                        //TODO
                },
                {
                        //TODO
                }
            )
            .also { compositeDisposable.add(it) }
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }

    //base on intent type to fire action
    abstract fun handleIntent(intent: I)

    //which is send event to GA4 / tracking logging
    abstract fun handleTracker(intent: I)

    fun initIntent(){
        handleIntent()
    }


}