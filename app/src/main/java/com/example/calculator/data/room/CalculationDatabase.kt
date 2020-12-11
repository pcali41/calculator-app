package com.example.calculator.data.room

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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

/**
 * A database that stores Calculation information and a global method to access
 * the database.
 */
@Database(entities = [Calculation::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CalculationDatabase : RoomDatabase() {
    /**
     * Connects the database to the DAO
     */
    abstract val calculationDatabaseDAO: CalculationDatabaseDAO

    /**
     * Defines a companion object containing a function for accessing the database
     */
    companion object {
        /**
         * Keeps a reference to any database returned via the getInstance method
         */
        private lateinit var INSTANCE: CalculationDatabase

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

                // If instance is uninitialized, build a new database instance
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        CalculationDatabase::class.java,
                        "calculation_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }

                return INSTANCE
            }
        }
    }
}

/**
 * A Dagger Hilt [Module] for providing an [CalculationDatabaseDAO] instance to
 * dependent ViewModel Components.
 */
@Module
@InstallIn(SingletonComponent::class)
object CalculationDatabaseModule {

    @Singleton
    @Provides
    fun provideCalculationDatabaseDAO(
        @ApplicationContext context: Context
    ): CalculationDatabaseDAO {
        return CalculationDatabase.getInstance(context).calculationDatabaseDAO
    }
}
