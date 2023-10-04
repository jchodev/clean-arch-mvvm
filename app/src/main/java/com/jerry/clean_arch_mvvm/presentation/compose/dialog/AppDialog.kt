package com.jerry.clean_arch_mvvm.presentation.compose.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.jerry.clean_arch_mvvm.base.R
import com.jerry.clean_arch_mvvm.jetpack_design_lib.dialog.MyDialog
import com.jerry.clean_arch_mvvm.marketpage.exception.MarketNotFoundException

@Composable
fun RetryDialog(mess: Any, doRetry :() -> Unit, onDismissRequest: ()-> Unit ){
    val message = getErrorMessage(mess)
    MyDialog(
        title = "",
        message = message,
        //usually navController.popBackStack()
        onDismissRequest = onDismissRequest,
        onOKRequest = doRetry,
        okStr = stringResource(R.string.retry)
    )
}

@Composable
private fun getErrorMessage(mess: Any) : String {
    var message = ""
    message = if (mess is String)
        mess
    else {
        if (mess is MarketNotFoundException) {
            stringResource(R.string.record_not_found_error)
        } else {
            stringResource(R.string.some_error)
        }
    }
    return message
}