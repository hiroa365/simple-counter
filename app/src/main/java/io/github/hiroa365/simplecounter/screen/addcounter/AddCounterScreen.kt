package io.github.hiroa365.simplecounter.screen.addcounter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@Composable
fun AddCounterScreen(
    viewModel: AddCounterViewModel = hiltViewModel(),
    navigateToMain: () -> Unit,
) {

    val state by viewModel.state.collectAsState()

    AddCounterScreen(
        name = state.name,
        onNameChange = { viewModel.nameChange(it) },
        onClickAdd = { viewModel.addCounter() },
        onClickBack = { navigateToMain() },
        onClickCancel = { navigateToMain() },
    )
}

@Composable
private fun AddCounterScreen(
    name: String,
    onNameChange: (String) -> Unit,
    onClickAdd: () -> Unit,
    onClickBack: () -> Unit,
    onClickCancel: () -> Unit,
) {
    Scaffold(
        topBar = {
            AddCounterTopBar(
                onClickBack = onClickBack,
            )
        },
        content = {
            AddCounterContent(
                name = name,
                onNameChange = onNameChange,
                onClickAdd = onClickAdd,
                onClickCancel = onClickCancel,
            )
        },
    )
}


@Composable
private fun AddCounterTopBar(
    onClickBack: () -> Unit,
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = onClickBack) {
                Icon(Icons.Default.ArrowBack, "")
            }
        },
        actions = { },
        elevation = 0.dp,
        backgroundColor = Color.White,
    )
}


@Composable
private fun AddCounterContent(
    name: String,
    onNameChange: (String) -> Unit,
    onClickAdd: () -> Unit,
    onClickCancel: () -> Unit,
) {
    Column {
        TextField(
            value = name,
            onValueChange = { onNameChange(it) },
            label = { Text(text = "カウンター名") }
        )
        Row {
            TextButton(onClick = onClickCancel) {
                Text(text = "キャンセル")
            }
            Button(onClick = onClickAdd) {
                Text(text = "追加")
            }
        }
    }
}


class AddCounterViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(initValue)
    val state = _state.asStateFlow()

    fun nameChange(value: String) {
        _state.value = _state.value.copy(name = value)
    }

    fun addCounter() {
        //TODO
    }
}

data class AddCounterState(
    val name: String,
)

val initValue = AddCounterState(
    name = "",
)

@Preview
@Composable
fun Preview() {
    AddCounterScreen(
        navigateToMain = {},
    )
}