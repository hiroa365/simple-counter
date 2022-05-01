package io.github.hiroa365.simplecounter.screen.addcategory

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import io.github.hiroa365.simplecounter.screen.category.CategoryItem
import javax.inject.Inject

@Composable
fun AddCategoryScreen(
    viewModel: AddCategoryViewModel = hiltViewModel(),
    navigateToCategory: () -> Unit
) {
}

@Composable
private fun AddCategoryScreen(
    navigateToCategory: () -> Unit
) {
    Button(onClick = navigateToCategory) {
        Text(text = "戻る")
    }
}

class AddCategoryViewModel @Inject constructor() : ViewModel() {

    fun add(item: CategoryItem) {

    }

}


@Preview
@Composable
fun Preview() {
    AddCategoryScreen(navigateToCategory = {})
}