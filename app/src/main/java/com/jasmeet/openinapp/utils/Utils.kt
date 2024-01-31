package com.jasmeet.openinapp.utils

import co.yml.charts.common.model.Point
import com.jasmeet.openinapp.data.DateValue
import java.text.SimpleDateFormat
import java.util.Locale


fun convertDateFormat(inputDate: String): String {
    val sdfInput = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val date = sdfInput.parse(inputDate)
    val sdfOutput = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return sdfOutput.format(date)
}

fun getLineChartData(listSize: Int, start: Int = 0, maxRange: Int): List<Point> {
    val list = arrayListOf<Point>()
    for (index in 0 until listSize) {
        list.add(
            Point(
                index.toFloat(),
                (start until maxRange).random().toFloat()
            )
        )
    }
    return list
}

fun getLineChartData(dateValues: List<DateValue>): List<Point> {
    return dateValues.filter { it.value.toFloat() != 0f }.mapIndexed { index, dateValue ->
        Point(
            index.toFloat(),
            dateValue.value.toFloat()
        )
    }
}
