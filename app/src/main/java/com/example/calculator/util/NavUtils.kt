package com.example.calculator.util

import androidx.annotation.StringRes
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument
import com.example.calculator.R

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int
) {
    class Argument(
        val name: String,
        val isOptional: Boolean = false,
        private val navArgBlock: NavArgumentBuilder.() -> Unit = {}
    ) {
        val prefix = if (isOptional) "?$name=" else "/"
        val route = "$prefix{$name}"

        fun toNavArgument() = navArgument(name, navArgBlock)
    }

    open val args: List<Argument> = listOf()

    val navArgs: List<NamedNavArgument>
        get() = args.map { it.toNavArgument() }

    val navGraphRoute: String
        get() = route.plus(args
            .map { arg -> arg.route }
            .reduceOrNull { acc, s -> acc.plus(s) } ?: ""
        )

    object Calculator : Screen(
        route = "calculator",
        resourceId = R.string.calculator,
    )

    object History : Screen(
        route = "history",
        resourceId = R.string.history
    )
}
