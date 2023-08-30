package com.jerry.clean_arch_mvvm.base.presentation

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jerry.clean_arch_mvvm.base.R
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
abstract class BaseFragment(@LayoutRes contentLayoutId : Int = 0) : Fragment(contentLayoutId) {

    lateinit var baseActivity: BaseActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseActivity = activity as BaseActivity
    }

    override fun onResume() {
        super.onResume()
        // baseActivity.showTitleBarBackIcon(showBack())
    }

    open fun showBack(): Boolean = false

    fun displayRetryDialog(mess: Any) {
        val message = getErrorMessage(mess)

        activity?.let {
                MaterialAlertDialogBuilder(it)
                    .setMessage(message)
                    .setPositiveButton(R.string.retry) { dialog, which ->
                        dialog.cancel()
                        dialog.dismiss()
                        doRetry()
                    }
                    .setNegativeButton(android.R.string.cancel) { dialog, which ->
                        dialog.dismiss()
                        afterClickedCancel()
                    }
                    .setCancelable(false)
                    .show()
        }
    }

    fun showLoading(show: Boolean) {
        baseActivity.showLoading(show)
    }

    abstract fun doRetry()

    open fun afterClickedCancel(){}

    open fun getErrorMessage(mess: Any) : String {
        var message = ""
        if (mess is String)
            message = mess
        else
            message = getString(R.string.some_error)
        return message
    }
}