package com.example.note.data

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class Converters {
    var format: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

    @TypeConverter
    fun dateToString(value: Date?): String? {
        return format.format(value)
    }

    @TypeConverter
    fun stringToDate(value: String?): Date? {
        return format.parse(value)
    }
}