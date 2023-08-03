package com.jerry.clean_arch_mvvm.base.utils

import java.text.SimpleDateFormat
import java.util.Calendar

class DisplayUtil {

    fun displayNormalText(str: String?) : String {
        return str ?: "---"
    }

    fun displayDateFormat(value: Long?): String {
        value?.let {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it
            val formatter = SimpleDateFormat("dd-MM-yyyy")
            return formatter.format(calendar.time)
        }

        return "---"
    }
}