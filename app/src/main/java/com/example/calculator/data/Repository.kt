package com.example.calculator.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.calculator.data.room.Calculation
import com.example.calculator.data.room.CalculationDatabaseDAO
import com.example.calculator.util.StringCalculator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

class Repository constructor(
    private val calculator: StringCalculator,
    private val database: CalculationDatabaseDAO
) {

    val currentExpression: MutableLiveData<String>
        get() = calculator.expression

    val currentResult: LiveData<String>
        get() = calculator.result

    fun apply(): Boolean = calculator.apply()

    fun addDigit(digit: Char) = calculator.addDigit(digit)

    fun addDecimal() = calculator.addDecimal()

    fun addOperator(operator: Operator) = calculator.addOperator(operator)

    fun delete() = calculator.delete()

    fun clear() = calculator.clear()

    fun saveCalculation(calculation: Calculation) = database.insert(calculation)

    fun getAllCalculations(): LiveData<List<Calculation>> = database.getAllCalculations()

    fun getCalculation(id: Long): Calculation? = database.get(id)

    fun clearHistory() = database.clear()
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        calculator: StringCalculator,
        database: CalculationDatabaseDAO
    ): Repository = Repository(calculator, database)
}
