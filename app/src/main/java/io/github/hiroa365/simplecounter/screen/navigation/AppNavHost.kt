package io.github.hiroa365.simplecounter.screen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.hiroa365.simplecounter.screen.addcategory.AddCategoryScreen
import io.github.hiroa365.simplecounter.screen.addcounter.AddCounterScreen
import io.github.hiroa365.simplecounter.screen.categorylist.CategoryListScreen
import io.github.hiroa365.simplecounter.screen.counterlist.CounterListScreen


@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Main.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Main.route) {
            CounterListScreen(
                navigateToCategory = { navController.navigate(Screen.Category.route) },
                navigateToAddCounter = {navController.navigate(Screen.AddCounter.route)}
            )
        }
        composable(route = Screen.Category.route) {
            CategoryListScreen(
                navigateToCounterList = { navController.navigate(Screen.Main.route) },
                navigateToAddCategory = { navController.navigate(Screen.AddCategory.route) },
            )
        }
        composable(route = Screen.AddCategory.route) {
            AddCategoryScreen(
                navigateToCategory = { navController.navigate(Screen.Category.route) },
            )
        }
        composable(route = Screen.AddCounter.route) {
            AddCounterScreen(
                navigateToMain = { navController.navigate(Screen.Main.route) },
            )
        }
    }
}
