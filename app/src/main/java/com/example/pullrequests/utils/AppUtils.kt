package com.example.pullrequests.utils

import java.util.*

object AppUtils {
    fun renderDateToIST(dateObj: Date?): Date {
        val date = dateObj?.time
        return if (date != null) {
            Date(date + TimeZone.getDefault().getOffset(Date().time))
        }
        else {
            Date(0)
        }
    }

    val locale = Locale("EN", "IN")
}