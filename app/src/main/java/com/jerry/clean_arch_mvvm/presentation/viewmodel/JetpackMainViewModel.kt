package com.jerry.clean_arch_mvvm.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class JetpackMainViewModel @Inject constructor(): ViewModel() {

    private val _showBackArrowLiveData = MutableLiveData<Boolean>()
    val showBackArrowLiveData: LiveData<Boolean>
        get() = _showBackArrowLiveData

    fun setShowBackArrowLiveData(value: Boolean){
        _showBackArrowLiveData.value = value
    }

    private val _showErrorDialogLiveData = MutableLiveData<Boolean>()
    val showErrorDialogLiveData: LiveData<Boolean>
        get() = _showErrorDialogLiveData

    fun setShowErrorDialogLiveData(value: Boolean){
        _showErrorDialogLiveData.value = value
    }
}