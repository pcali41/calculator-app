package com.example.calculator.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "calculation_history_table")
data class Calculation(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "expression")
    val expression: String,

    @ColumnInfo(name = "result")
    val result: String,

    @ColumnInfo(name = "time_calculated")
    val timeCalculated: OffsetDateTime = OffsetDateTime.now()
)