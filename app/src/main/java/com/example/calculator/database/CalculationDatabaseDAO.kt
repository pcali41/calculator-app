package com.example.calculator.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Defines methods for using the Calculation class with Room
 */
@Dao
interface CalculationDatabaseDAO {

    /**
     * Inserts a new row into the table
     */
    @Insert
    fun insert(calculation: Calculation)

    /**
     * Selects and returns the row with the given row id
     */
    @Query("SELECT * from calculation_history_table WHERE id = :key")
    fun get(key: Long): Calculation?

    /**
     * Selects and returns all rows in the table sorted by when they were calculated
     */
    @Query("SELECT * FROM calculation_history_table ORDER BY datetime(time_calculated)")
    fun getAllCalculations(): LiveData<List<Calculation>>

    /**
     * Deletes all rows from the table
     */
    @Query("DELETE FROM calculation_history_table")
    fun clear()
}