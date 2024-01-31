package com.jasmeet.openinapp.utils

import java.text.SimpleDateFormat
import java.util.Locale


fun convertDateFormat(inputDate: String): String {
    val sdfInput = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val date = sdfInput.parse(inputDate)
    val sdfOutput = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return sdfOutput.format(date)
}