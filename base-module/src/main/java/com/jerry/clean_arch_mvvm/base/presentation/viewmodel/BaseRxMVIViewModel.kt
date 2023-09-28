package com.jerry.clean_arch_mvvm.base.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jerry.clean_arch_mvvm.base.presentation.mvi.MviAction

import com.jerry.clean_arch_mvvm.base.presentation.mvi.MviIntent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

//this is a base class which is used for MVI with RxJava PublishSubject
abstract class BaseRxMVIViewModel<INTENT: MviIntent, ACTION: MviAction>(
    open var dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var onIntentPublishSubjectDisposable: Disposable? = null

    private val onIntentPublishSubject: PublishSubject<INTENT> by lazy { PublishSubject.create() }

    fun sendIntent(intent: INTENT) {
        onIntentPublishSubject.onNext(intent)
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
                    handleIntentTracker(it)
            }
            .map {
                handleIntent(it)
            }
            .doOnNext {
                handleActionTracker(it)

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

    //base on intent type to map action
    protected abstract fun handleIntent(intent: INTENT): ACTION

    //base on action type to do something
    protected abstract fun handleAction(action: ACTION)

    //which is send event to GA4 / tracking logging
    protected abstract fun handleIntentTracker(intent: INTENT)
    //which is send event to GA4 / tracking logging
    protected abstract fun handleActionTracker(action: ACTION)

    fun initIntent(){
        onIntentPublishSubjectDisposable = observeOnIntentSubject()
    }


}