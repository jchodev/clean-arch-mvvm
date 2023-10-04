package com.jerry.clean_arch_mvvm.base.presentation

sealed class UiState<out T> where T : Any? {
    object Initial : UiState<Nothing>()
    object Loading : UiState<Nothing>()

    data class Success<T>(val data: T) : UiState<T>()
    data class Failure(val errorAny: Any) : UiState<Nothing>()
    //which is from server provided message
    data class CustomerError(val errorMessage: String) : UiState<Nothing>()
}