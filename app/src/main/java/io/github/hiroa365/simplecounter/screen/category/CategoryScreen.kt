@file:OptIn(ExperimentalMaterialApi::class)

package io.github.hiroa365.simplecounter.screen.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import io.github.hiroa365.simplecounter.screen.main.MainScreenBottomBar
import io.github.hiroa365.simplecounter.screen.main.MainScreenContent
import io.github.hiroa365.simplecounter.screen.main.MainScreenTopBar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*
import javax.inject.Inject

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    CategoryScreen(
        categoryList = state.categoryList,
        onClickAdd = {},
        onClickCategory = {},
        onClickEdit = {},
    )
}

@Composable
fun CategoryScreen(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    categoryList: List<CategoryItem>,
    onClickAdd: () -> Unit,
    onClickCategory: (UUID) -> Unit,
    onClickEdit: (UUID) -> Unit,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { CategoryScreenTopBar(onClickAdd) },
        bottomBar = { },
        content = {
            MainScreenContent(
                categoryList = categoryList,
                onClickCategory = onClickCategory,
                onClickEdit = onClickEdit,
            )
        }
    )
}

@Composable
fun CategoryScreenTopBar(
    onClickAdd: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = "カテゴリ一覧") },
        navigationIcon = null,
        actions = {
            IconButton(onClick = { onClickAdd() }) {
                Icon(Icons.Default.Add, "")
            }
//            IconButton(onClick = { /*TODO*/ }) {
//                Icon(Icons.Default.MoreVert, "")
//            }
        },
        elevation = 0.dp,
        backgroundColor = Color.White,
    )
}

@Composable
fun MainScreenContent(
    categoryList: List<CategoryItem>,
    onClickCategory: (UUID) -> Unit,
    onClickEdit: (UUID) -> Unit,
) {
    LazyColumn {
        items(items = categoryList) {
            CategoryItem(item = it, onClickCategory = onClickCategory, onClickEdit = onClickEdit)
        }
    }
}

@Composable
fun CategoryItem(
    item: CategoryItem,
    onClickCategory: (UUID) -> Unit,
    onClickEdit: (UUID) -> Unit,
) {
    Card(
        onClick = { onClickCategory(item.id) },
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth()
            .padding(4.dp),
        elevation = 8.dp,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.h5
                )
            }
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colors.primary)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = { onClickEdit(item.id) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    CategoryScreen(
        categoryList = initValue.categoryList,
        onClickAdd = {},
        onClickCategory = {},
        onClickEdit = {},
    )
}


class CategoryViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(initValue)
    val state = _state.asStateFlow()
}

private val initValue = CategoryScreenState(
    categoryList = listOf(
        CategoryItem(id = UUID.randomUUID(), name = "カテゴリ１"),
        CategoryItem(id = UUID.randomUUID(), name = "カテゴリ２"),
    )
)

data class CategoryScreenState(
    val categoryList: List<CategoryItem>,
)

data class CategoryItem(
    val id: UUID,
    val name: String,
)
