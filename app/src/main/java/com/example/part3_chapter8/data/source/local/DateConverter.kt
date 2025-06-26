package com.example.part3_chapter8.data.source.local

import androidx.room.TypeConverter
import com.example.part3_chapter8.util.DateUtil
import java.util.Date

class DateConverter {

    @TypeConverter
    fun toDate(timestamp: String?) : Date? {
        return timestamp?.let { DateUtil.dbDateFormat.parse(it) }
    }

    @TypeConverter
    fun toTimeStamp(date: Date?) : String? {
        return date?.let { DateUtil.dbDateFormat.format(it) }
    }
}