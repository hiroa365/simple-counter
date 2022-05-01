@file:OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)

package io.github.hiroa365.simplecounter.screen.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.hiroa365.simplecounter.foundation.ToggledButton
import java.util.*


/**
 * stateful
 */
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel(),
    navigateToCategory: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    MainScreen(
        counterItems = state.counterItems,
        mode = state.mode,
        onClickCard = { uuid ->
            when (state.mode) {
                CounterMode.Up -> viewModel.sendEvent(CountUp(uuid))
                CounterMode.Down -> viewModel.sendEvent(CountDown(uuid))
                CounterMode.Edit -> { /* TODO */
                }
            }
        },
//        onClickCountDown = { event -> viewModel.sendEvent(event) },
        onClickCountClear = { event -> viewModel.sendEvent(event) },
        onChangeMode = { mode -> viewModel.sendEvent(ChangeMode(mode)) },
        onClickBack = { navigateToCategory() },
        onClickAdd = { /*TODO*/ },
    )
}

/**
 * stateless
 */
@Composable
private fun MainScreen(
    counterItems: List<CounterItem>,
    mode: CounterMode,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onClickCard: (UUID) -> Unit,
//    onClickCountDown: (MainScreenEvent) -> Unit,
    onClickCountClear: (MainScreenEvent) -> Unit,
    onChangeMode: (CounterMode) -> Unit,
    onClickBack: () -> Unit,
    onClickAdd: () -> Unit,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { MainScreenTopBar(onClickBack = onClickBack, onClickAdd = onClickAdd) },
        bottomBar = { MainScreenBottomBar(mode = mode, onChangeMode = onChangeMode) },
        content = {
            MainScreenContent(
                counterItems = counterItems,
                onClickCard = onClickCard
            )
        }
    )
}

@Composable
private fun MainScreenTopBar(
    onClickBack: () -> Unit,
    onClickAdd: () -> Unit,
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = onClickBack) {
                Icon(Icons.Default.ArrowBack, "")
            }
        },
        actions = {
            IconButton(onClick = onClickAdd) {
                Icon(Icons.Default.Add, "")
            }
        },
        elevation = 0.dp,
        backgroundColor = Color.White,
    )
}

@Composable
private fun MainScreenBottomBar(
    mode: CounterMode,
    onChangeMode: (CounterMode) -> Unit,
) {
    BottomAppBar(
        backgroundColor = Color.White,
        elevation = 0.dp,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        ToggledButton(
            items = listOf("+", "-", "編集"),
            selectedIndex = when (mode) {
                CounterMode.Up -> 0
                CounterMode.Down -> 1
                CounterMode.Edit -> 2
            },
            indexChanged = { index ->
                when (index) {
                    0 -> onChangeMode(CounterMode.Up)
                    1 -> onChangeMode(CounterMode.Down)
                    2 -> onChangeMode(CounterMode.Edit)
                }
            }
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun MainScreenContent(
    counterItems: List<CounterItem>,
    onClickCard: (UUID) -> Unit,
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
    ) {
        items(items = counterItems) { item ->
            CardContent(item, onClickCard)
        }
    }
}

@Composable
private fun CardContent(
    item: CounterItem,
    onClickCard: (UUID) -> Unit,
) {
    Card(
        onClick = { onClickCard(item.id) },
        modifier = Modifier
            .size(120.dp)
//            .fillMaxSize()
            .padding(4.dp),
        elevation = 8.dp,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = item.counter.toString(),
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    maxLines = 1,
                )
            }
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colors.primarySurface)
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = item.name,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    maxLines = 1,
                )
            }
        }
    }
}



@Preview
@Composable
fun Preview() {

    MainScreen(
        counterItems = initValue.counterItems.filter { it.categoryId == categoryId2 },
        mode = initValue.mode,
        onClickCard = { },
//        onClickCountDown = { },
        onClickCountClear = { },
        onChangeMode = {},
        onClickBack = { },
        onClickAdd = { },
    )
}