@file:OptIn(ExperimentalMaterialApi::class)

package io.github.hiroa365.simplecounter.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.hiroa365.simplecounter.foundation.noRippleClickable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/**
 * stateful
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) {
    val state by viewModel.state.collectAsState()

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = remember { BottomSheetState(BottomSheetValue.Collapsed) }
    )
    //BottomSheet表示
    val onClickShown: () -> Unit = {
        coroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.expand() }
    }
    //BottomSheet非表示
    val onClickHide: () -> Unit = {
        coroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.collapse() }
    }

    MainScreen(
        counter = state.counter,
        bottomSheetScaffoldState = bottomSheetScaffoldState,
        onClickCountUp = { viewModel.onEvent(OnEventCountUp) },
        onClickCountClear = { viewModel.onEvent(OnEventCountClear) },
        onClickShown = onClickShown,
        onClickHide = onClickHide,
    )
}

/**
 * stateless
 */
@Composable
fun MainScreen(
    counter: Long,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    onClickCountUp: () -> Unit,
    onClickCountClear: () -> Unit,
    onClickShown: () -> Unit,
    onClickHide: () -> Unit,
) {
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            MainBottomSheet(
                onClickCountClear = onClickCountClear,
                onClickHide = onClickHide,
            )
        },
        sheetPeekHeight = 0.dp,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colors.secondary,
                                Color.Yellow
                            )
                        )
                    )
                    .noRippleClickable { onClickCountUp() },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = counter.toString(),
                    fontSize = 50.sp,
                    color = MaterialTheme.colors.onPrimary
                )
            }
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onClickShown) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = Color.DarkGray
                    )
                }
            }
        }
    }
}
