package com.alex.assignmentfor4ksoft.utils.converters

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateTimeConverter {
    companion object{
        fun convertLongToTime(time: Long): String {
            val date = Date(time)
            val format = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())
            return format.format(date)
        }
    }
}