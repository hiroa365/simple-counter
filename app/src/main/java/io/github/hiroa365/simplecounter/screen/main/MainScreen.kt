@file:OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)

package io.github.hiroa365.simplecounter.screen.main

import android.util.Log
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
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.hiroa365.simplecounter.foundation.ToggledButton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*
import javax.inject.Inject


/**
 * stateful
 */
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel(),
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
    )
}

/**
 * stateless
 */
@Composable
fun MainScreen(
    counterItems: List<CounterItem>,
    mode: CounterMode,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onClickCard: (UUID) -> Unit,
//    onClickCountDown: (MainScreenEvent) -> Unit,
    onClickCountClear: (MainScreenEvent) -> Unit,
    onChangeMode: (CounterMode) -> Unit,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { MainScreenTopBar() },
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
fun MainScreenTopBar() {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.ArrowBack, "")
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Add, "")
            }
        },
        elevation = 0.dp,
        backgroundColor = Color.White,
    )
}

@Composable
fun MainScreenBottomBar(
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
fun MainScreenContent(
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
fun CardContent(
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


@HiltViewModel
class MainScreenViewModel @Inject constructor() : ViewModel() {
    private val TAG = javaClass.simpleName

    private val _state: MutableStateFlow<MainScreenState> = MutableStateFlow(initValue)
    val state: StateFlow<MainScreenState> = _state.asStateFlow()

    fun sendEvent(event: MainScreenEvent) {
        when (event) {
            is CountUp -> {
                Log.i(TAG, "count up uuid:$event")
                val newCounterItems = _state.value.counterItems.map {
                    if (it.id == event.uuid && it.counter < Int.MAX_VALUE) {
                        it.copy(counter = it.counter + 1)
                    } else it
                }
                _state.value = _state.value.copy(counterItems = newCounterItems)
            }
            is CountDown -> {
                Log.i(TAG, "count down uuid:$event")
                val newCounterItems = _state.value.counterItems.map {
                    if (it.id == event.uuid && it.counter > 0) {
                        it.copy(counter = it.counter - 1)
                    } else it
                }
                _state.value = _state.value.copy(counterItems = newCounterItems)
            }
            is CountReset -> {
                Log.i(TAG, "count reset uuid:$event")
                val newCounterItems = _state.value.counterItems.map {
                    if (it.id == event.uuid) it.copy(counter = 0)
                    else it
                }
                _state.value = _state.value.copy(counterItems = newCounterItems)
            }
            is ChangeMode -> {
                Log.i(TAG, "change mode:${event.mode}")
                _state.value = _state.value.copy(mode = event.mode)
            }
        }
    }
}

sealed class MainScreenEvent
data class CountUp(val uuid: UUID) : MainScreenEvent()
data class CountDown(val uuid: UUID) : MainScreenEvent()
data class CountReset(val uuid: UUID) : MainScreenEvent()
data class ChangeMode(val mode: CounterMode) : MainScreenEvent()



val categoryId = UUID.randomUUID()
val categoryId2 = UUID.randomUUID()

private val initValue = MainScreenState(
    counterItems = mutableListOf(
        CounterItem(id = UUID.randomUUID(), categoryId = categoryId, counter = Int.MAX_VALUE, name = "項目A"),
        CounterItem(id = UUID.randomUUID(), categoryId = categoryId, counter = 1, name = "項目B"),
        CounterItem(id = UUID.randomUUID(), categoryId = categoryId2, counter = 2, name = "項目C"),
        CounterItem(id = UUID.randomUUID(), categoryId = categoryId2, counter = Int.MAX_VALUE, name = "項目D"),
    ),
    mode = CounterMode.Up,
)

data class MainScreenState(
    val counterItems: List<CounterItem>,
    val mode: CounterMode
)

//カウンター
data class CounterItem(
    val id: UUID,
    val categoryId: UUID,
    val counter: Int,
    val name: String,
)

sealed class CounterMode {
    object Up : CounterMode()
    object Down : CounterMode()
    object Edit : CounterMode()
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
        onChangeMode = {}
    )
}