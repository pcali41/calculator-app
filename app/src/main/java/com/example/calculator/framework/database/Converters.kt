package com.example.calculator.framework.database

import androidx.room.TypeConverter
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

/**
 * Utility class that provides an interface for converting an [OffsetDateTime]
 * to a [String] format and vice-versa.
 *
 * The CalculationDatabase will be making use of this to store [OffsetDateTime]
 * values in a recognizable format.
 */
object Converters {
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    /**
     * Converts a [String] to an [OffsetDateTime].
     */
    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String?): OffsetDateTime? {
        return value?.let {
            return formatter.parse(value, OffsetDateTime::from)
        }
    }

    /**
     * Converts an [OffsetDateTime] to a [String].
     */
    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(date: OffsetDateTime?): String? {
        return date?.format(formatter)
    }
}