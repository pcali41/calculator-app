package com.example.calculator.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.setContent
import com.example.calculator.ui.calculator.CalculatorViewModel
import com.example.calculator.ui.history.HistoryViewModel
import com.example.calculator.ui.theme.CalculatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val calculatorViewModel: CalculatorViewModel by viewModels()
    private val historyViewModel: HistoryViewModel by viewModels()

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    CalculatorApp(
                        calculatorViewModel = calculatorViewModel,
                        historyViewModel = historyViewModel
                    )
                }
            }
        }
    }
}