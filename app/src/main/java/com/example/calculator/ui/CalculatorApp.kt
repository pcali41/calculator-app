package com.example.calculator.ui

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calculator.data.room.Calculation
import com.example.calculator.ui.calculator.CalculatorScreen
import com.example.calculator.ui.calculator.CalculatorViewModel
import com.example.calculator.ui.history.HistoryScreen
import com.example.calculator.ui.history.HistoryViewModel
import com.example.calculator.util.Screen

@ExperimentalMaterialApi
@Composable
fun CalculatorApp(
    calculatorViewModel: CalculatorViewModel,
    historyViewModel: HistoryViewModel
)
{
    // Create/Remember a NavHostController to manage screen navigation
    val navController = rememberNavController()

    // Create/Remember a ScaffoldState to manage shared objects like snackbars
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    // Get the current expression and result values from the CalculatorViewModel
    val expression by calculatorViewModel.expression.observeAsState("")
    val result by calculatorViewModel.result.observeAsState("")

    // Get the latest list of history items from the HistoryViewModel
    val historyItems: List<Calculation>
            by historyViewModel.historyItems.observeAsState(listOf())

    // Setup and display the app's navigation graph using the navController
    NavHost(navController, startDestination = Screen.Calculator.navGraphRoute) {
        composable(Screen.Calculator.navGraphRoute) {
            CalculatorScreen(
                navController = navController,
                scaffoldState = scaffoldState,
                expression = expression,
                result = result,
                onAddDigit = calculatorViewModel::onAddDigit,
                onAddDecimal = calculatorViewModel::onAddDecimal,
                onAddOperator = calculatorViewModel::onAddOperator,
                onApply = calculatorViewModel::onApply,
                onDelete = calculatorViewModel::onDelete,
                onClear = calculatorViewModel::onClear
            )
        }
        composable(Screen.History.navGraphRoute) {
            HistoryScreen(
                navController = navController,
                scaffoldState = scaffoldState,
                scope = scope,
                historyItems = historyItems,
                clearHistory = historyViewModel::clearHistory,
                recallExpression = historyViewModel::recallExpression
            )
        }
    }
}
