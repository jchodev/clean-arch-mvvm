package com.jerry.clean_arch_mvvm.base.presentation

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity() : AppCompatActivity() {

    abstract fun showLoading(show: Boolean)
}