package com.jerry.clean_arch_mvvm.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class JetpackMainViewModel @Inject constructor(
): ViewModel() {

    private val _showBackArrowLiveData = MutableLiveData<Boolean>(true)
    val showBackArrowLiveData: LiveData<Boolean>
        get() = _showBackArrowLiveData

}