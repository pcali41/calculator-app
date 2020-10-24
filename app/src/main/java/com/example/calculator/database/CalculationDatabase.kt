package com.example.calculator.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * A database that stores Calculation information and a global method to access
 * the database.
 */
@Database(entities = [Calculation::class], version = 1, exportSchema = false)
abstract class CalculationDatabase : RoomDatabase() {

    /**
     * Connects the database to the DAO
     */
    abstract val calculationDatabaseDAO: CalculationDatabaseDAO

    /**
     * Defines a companion object, containing functions for accessing the database
     */
    companion object {
        /**
         * Keeps a reference to any database returned via the getInstance method
         */
        @Volatile
        private var INSTANCE: CalculationDatabase? = null

        /**
         * Helper function to get the database instance.
         *
         * If the database hasn't been initialized yet, it constructs it first.
         *
         * @param context The application context Singleton, used to get access to the filesystem
         */
        fun getInstance(context: Context): CalculationDatabase {
            // Handle multiple calls to this function one thread at a time
            synchronized(this) {
                // Copy the current value of INSTANCE to a local variable so Kotlin can smart cast.
                // Smart case is only available to local variables.
                var instance = INSTANCE

                // If instance is uninitialized, build a new database instance
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CalculationDatabase::class.java,
                        "calculation_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}