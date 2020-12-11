package com.example.calculator.ui.history

import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.calculator.R
import com.example.calculator.data.room.Calculation
import com.example.calculator.util.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
private fun HistoryScreenPreview() {
    HistoryScreen(
        navController = rememberNavController(),
        scaffoldState = rememberScaffoldState(),
        scope = rememberCoroutineScope(),
        historyItems = listOf(),
        clearHistory = {},
        recallExpression = {}
    )
}

@ExperimentalMaterialApi
@Composable
fun HistoryScreen(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    historyItems: List<Calculation>,
    clearHistory: () -> Unit,
    recallExpression: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = {
            HistoryScreenTopBar(
                navController = navController,
                scaffoldState = scaffoldState,
                scope = scope,
                clearHistory = clearHistory
            )
        }
    ) { innerPadding ->
        HistoryScreenBodyContent(
            navController = navController,
            calculations = historyItems,
            recallExpression = recallExpression,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@ExperimentalMaterialApi
@Composable
private fun HistoryScreenTopBar(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    clearHistory: () -> Unit,
    modifier: Modifier = Modifier
) {
    val historyScreenTitle = stringResource(Screen.History.resourceId)
    val historyClearedMessage = stringResource(R.string.cleared_history_message)

    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.Default.ArrowBack)
            }
        },
        title = { Text(historyScreenTitle) },
        actions = {
            IconButton(
                onClick = {
                    clearHistory()

                    // Show a snackbar to show that the history has been cleared
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = historyClearedMessage
                        )
                    }

                    navController.navigateUp()
                }
            ) {
                Icon(Icons.Default.DeleteForever)
            }
        }
    )
}

@Composable
private fun HistoryScreenBodyContent(
    navController: NavHostController,
    calculations: List<Calculation>,
    recallExpression: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumnFor(
        items = calculations,
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(top = 8.dp, start = 16.dp, end = 16.dp)
    ) { item ->
        HistoryItemRow(
            item = item,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    recallExpression(item.expression)

                    with(navController) {
                        popBackStack(graph.startDestination, true)
                        navigate(Screen.Calculator.route)
                    }
                })
        )
    }
}

@Composable
private fun HistoryItemRow(
    item: Calculation,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ScrollableRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = item.expression,
                style = MaterialTheme.typography.h6,
                color = Color.DarkGray
            )
        }
        ScrollableRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = item.result,
                style = MaterialTheme.typography.h4,
            )
        }
    }
}
