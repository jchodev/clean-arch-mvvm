package com.jerry.clean_arch_mvvm.base.usecase

sealed class UseCaseResult<out T> {
    data class Success<T>(val data: T) : UseCaseResult<T>()
    //which is provided by BE
    data class CustomerError(val error: String): UseCaseResult<Nothing>()
    data class Failure(val throwable: Throwable) : UseCaseResult<Nothing>()
}