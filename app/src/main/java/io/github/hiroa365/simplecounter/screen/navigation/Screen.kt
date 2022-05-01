package io.github.hiroa365.simplecounter.screen.navigation


sealed class Screen(val route: String) {
    object Main : Screen(route = "main")
    object Category : Screen(route = "category")
    object AddCategory : Screen(route = "add-category")
}
