@file:OptIn(ExperimentalMaterialApi::class)

package io.github.hiroa365.simplecounter.screen.categorylist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.hiroa365.simplecounter.data.repository.CategoryItem
import io.github.hiroa365.simplecounter.data.repository.CategoryItemRepositoryMock
import java.util.*

@Composable
fun CategoryListScreen(
    viewModel: CategoryListViewModel = hiltViewModel(),
    navigateToCounterList: (Long) -> Unit,
    navigateToAddCategory: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    CategoryListScreen(
        categoryList = state.categoryList,
        onClickAddCategory = { navigateToAddCategory() },
        onClickCategory = { navigateToCounterList(it) },
        onClickEdit = {},
    )
}

@Composable
private fun CategoryListScreen(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    categoryList: List<CategoryItem>,
    onClickAddCategory: () -> Unit,
    onClickCategory: (Long) -> Unit,
    onClickEdit: (Long) -> Unit,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { CategoryListTopBar(onClickAddCategory) },
        bottomBar = { },
        content = {
            CategoryListContent(
                categoryList = categoryList,
                onClickCategory = onClickCategory,
                onClickEdit = onClickEdit,
            )
        }
    )
}

@Composable
private fun CategoryListTopBar(
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
private fun CategoryListContent(
    categoryList: List<CategoryItem>,
    onClickCategory: (Long) -> Unit,
    onClickEdit: (Long) -> Unit,
) {
    LazyColumn {
        items(items = categoryList) {
            CategoryItem(
                item = it,
                onClickCategory = onClickCategory,
                onClickEdit = onClickEdit,
            )
        }
    }
}

@Composable
private fun CategoryItem(
    item: CategoryItem,
    onClickCategory: (Long) -> Unit,
    onClickEdit: (Long) -> Unit,
) {
    Card(
        onClick = { onClickCategory(item.categoryId) },
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth()
            .padding(4.dp),
        elevation = 8.dp,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .fillMaxWidth(),
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
                    .background(item.color)
                    .padding(0.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = { onClickEdit(item.categoryId) }) {
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
    CategoryListScreen(
        categoryList = CategoryItemRepositoryMock().getAll(),
        onClickAddCategory = {},
        onClickCategory = {},
        onClickEdit = {},
    )
}
