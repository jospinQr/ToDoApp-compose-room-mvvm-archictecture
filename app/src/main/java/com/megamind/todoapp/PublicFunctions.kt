package com.megamind.todoapp

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date

class DateConverter {

    @TypeConverter
    fun toDate(timestamp: Long): Date {
        return Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: Date): Long {
        return date.time
    }
}

fun dateToString(date: Date): String {

    val formatter = SimpleDateFormat("dd/MM/yyy hh:mm")

    return formatter.format(date)
}