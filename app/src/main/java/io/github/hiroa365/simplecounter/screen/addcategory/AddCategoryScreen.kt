package io.github.hiroa365.simplecounter.screen.addcategory

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.hiroa365.simplecounter.data.repository.CategoryItem
import io.github.hiroa365.simplecounter.data.repository.CategoryItemRepository
import io.github.hiroa365.simplecounter.ui.theme.Purple200
import io.github.hiroa365.simplecounter.ui.theme.Purple500
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*
import javax.inject.Inject

@Composable
fun AddCategoryScreen(
    viewModel: AddCategoryViewModel = hiltViewModel(),
    navigateToCategory: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    AddCategoryScreen(
        categoryName = state.categoryName,
        onCategoryNameChange = { viewModel.onCategoryNameChange(it) },
        onClickAdd = {
            viewModel.addCategory()
            navigateToCategory()
        },
        onClickCancel = { navigateToCategory() },
    )
}

@Composable
private fun AddCategoryScreen(
    categoryName: String,
    onCategoryNameChange: (String) -> Unit,
    onClickAdd: () -> Unit,
    onClickCancel: () -> Unit
) {
    Scaffold(
        topBar = { AddCategoryTopBar(onClickBack = onClickCancel) },
        content = {
            AddCategoryContent(
                categoryName = categoryName,
                onCategoryNameChange = onCategoryNameChange,
                onClickAdd = onClickAdd,
                onClickCancel = onClickCancel,
            )
        }
    )
}


@Composable
private fun AddCategoryTopBar(
    onClickBack: () -> Unit,
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = onClickBack) {
                Icon(Icons.Default.ArrowBack, "")
            }
        },
        actions = {
        },
        elevation = 0.dp,
        backgroundColor = Color.White,
    )
}


@Composable
private fun AddCategoryContent(
    categoryName: String,
    onCategoryNameChange: (String) -> Unit,
    onClickAdd: () -> Unit,
    onClickCancel: () -> Unit,
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    )  {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = categoryName,
            onValueChange = { onCategoryNameChange(it) },
            label = { Text(text = "カテゴリ名") }
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onClickCancel) {
                Text(text = "キャンセル")
            }

            Spacer(modifier = Modifier.padding(16.dp))

            Button(onClick = onClickAdd) {
                Text(text = "追加")
            }
        }
    }
}

@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    private val categoryItemRepository: CategoryItemRepository
) : ViewModel() {

    private val _state = MutableStateFlow(initValue)
    val state = _state.asStateFlow()


    fun onCategoryNameChange(value: String) {
        _state.value = _state.value.copy(categoryName = value)
    }

    fun addCategory() {
        if (_state.value.categoryName.isEmpty()) return

        val item = CategoryItem(
            categoryId = UUID.randomUUID().mostSignificantBits,
            name = _state.value.categoryName,
            color = _state.value.color,
        )
        categoryItemRepository.add(item)
    }
}

data class AddCategoryState(
    /**
     * カテゴリ名
     */
    val categoryName: String,
    /**
     * 色
     */
    val color: Color = Purple500, /* TODO */
)

val initValue = AddCategoryState(
    categoryName = "",
    color = Purple500,
)


@Preview
@Composable
fun Preview() {
    AddCategoryScreen(
        navigateToCategory = {},
    )
}