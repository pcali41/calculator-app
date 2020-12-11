package com.example.calculator.ui.calculator

import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.calculator.data.Operator
import com.example.calculator.ui.theme.typography
import com.example.calculator.util.Screen
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
private fun CalculatorScreenPreview() {
    CalculatorScreen(
        navController = rememberNavController(),
        scaffoldState = rememberScaffoldState(),
        expression = "1+2+3+4+5+6+7+8+9+10",
        result = "55",
        onAddDigit = {},
        onAddDecimal = {},
        onAddOperator = {},
        onApply = {},
        onDelete = {},
        onClear = {}
    )
}

@Composable
fun CalculatorScreen(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    expression: String,
    result: String,
    onAddDigit: (Char) -> Unit,
    onAddDecimal: () -> Unit,
    onAddOperator: (Operator) -> Unit,
    onApply: () -> Unit,
    onDelete: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = { CalculatorScreenTopBar(navController) }
    ) { innerPadding ->
        CalculatorScreenBodyContent(
            expression = expression,
            result = result,
            onAddDigit = onAddDigit,
            onAddDecimal = onAddDecimal,
            onAddOperator = onAddOperator,
            onApply = onApply,
            onDelete = onDelete,
            onClear = onClear,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun CalculatorScreenTopBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val calculatorScreenTitle = stringResource(Screen.Calculator.resourceId)

    TopAppBar(
        modifier = modifier,
        title = { Text(calculatorScreenTitle) },
        actions = {
            IconButton(
                onClick = { navController.navigate(Screen.History.route) }
            ) {
                Icon(Icons.Default.History)
            }
        }
    )
}

@Composable
private fun CalculatorScreenBodyContent(
    expression: String,
    result: String,
    onAddDigit: (Char) -> Unit,
    onAddDecimal: () -> Unit,
    onAddOperator: (Operator) -> Unit,
    onApply: () -> Unit,
    onDelete: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxHeight()
    ) {
        Column(modifier = Modifier
            .weight(0.35f)
            .padding(horizontal = 8.dp)
        ) {
            CalculatorOutputWindow(
                text = expression,
                textStyle = typography.h2,
                modifier = Modifier.weight(0.55f)
            )
            CalculatorOutputWindow(
                text = result,
                textStyle = typography.h4,
                textColor = Color.DarkGray,
                modifier = Modifier.weight(0.45f)
            )
        }
        CalculatorButtonGrid(
            onAddDigit = onAddDigit,
            onAddDecimal = onAddDecimal,
            onAddOperator = onAddOperator,
            onApply = onApply,
            onDelete = onDelete,
            onClear = onClear,
            modifier = Modifier.weight(0.65f)
        )
    }
}

@Composable
private fun CalculatorOutputWindow(
    text: String,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Unspecified
) {
    // Create and remember a ScrollState starting at the max value
    val scrollState = rememberScrollState(1.0f)

    ScrollableRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Top,
        scrollState = scrollState
    ) {
        Text(
            text = text,
            color = textColor,
            style = textStyle
        )
    }

    // Scroll to the end of the ScrollableRow if the expression has changed.
    var scrollToEnd = false
    remember(text) { scrollToEnd = true }

    if (scrollToEnd) {
        val scope = rememberCoroutineScope()
        scope.launch {
            scrollState.smoothScrollTo(scrollState.maxValue)
        }
    }
}
