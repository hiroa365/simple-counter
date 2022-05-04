package io.github.hiroa365.simplecounter.screen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.github.hiroa365.simplecounter.screen.addcategory.AddCategoryScreen
import io.github.hiroa365.simplecounter.screen.addcounter.AddCounterScreen
import io.github.hiroa365.simplecounter.screen.categorylist.CategoryListScreen
import io.github.hiroa365.simplecounter.screen.counterlist.CounterListScreen


object Destinations {
    const val CounterList = "counter-list"
    const val CategoryList = "category-list"
    const val AddCategory = "add-category"
    const val AddCounter = "add-counter"
}

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.CategoryList,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(route = Destinations.CategoryList) {
            CategoryListScreen(
                navigateToCounterList = { navController.navigate("${Destinations.CounterList}/$it") },
                navigateToAddCategory = { navController.navigate(Destinations.AddCategory) },
            )
        }

        composable(route = Destinations.AddCategory) {
            AddCategoryScreen(
                navigateToCategory = { navController.navigate(Destinations.CategoryList) },
            )
        }

        composable(
            route = "${Destinations.CounterList}/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.LongType })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val categoryId = arguments.getLong("categoryId")
            CounterListScreen(
                categoryId = categoryId,
                navigateToCategory = { navController.navigate(Destinations.CategoryList) },
                navigateToAddCounter = { navController.navigate("${Destinations.AddCounter}/$categoryId") }
            )
        }

        composable(
            route = "${Destinations.AddCounter}/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.LongType })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val categoryId = arguments.getLong("categoryId")
            AddCounterScreen(
                categoryId = categoryId,
                navigateToCounterList = { navController.navigate("${Destinations.AddCounter}/$it") },
            )
        }
    }
}
