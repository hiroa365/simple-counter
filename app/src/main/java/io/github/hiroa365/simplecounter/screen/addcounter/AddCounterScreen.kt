package io.github.hiroa365.simplecounter.screen.addcounter

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.hiroa365.simplecounter.data.repository.CounterItem
import io.github.hiroa365.simplecounter.data.repository.CounterItemRepository
import io.github.hiroa365.simplecounter.ui.theme.Purple200
import io.github.hiroa365.simplecounter.ui.theme.Purple500
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*
import javax.inject.Inject

@Composable
fun AddCounterScreen(
    categoryId: Long,
    viewModel: AddCounterViewModel = hiltViewModel(),
    navigateToCounterList: (Long) -> Unit,
) {

    val state by viewModel.state.collectAsState()

    AddCounterScreen(
        name = state.name,
        onNameChange = { viewModel.nameChange(it) },
        onClickAdd = {
            viewModel.addCounter(categoryId)
            navigateToCounterList(categoryId)
        },
        onClickBack = { navigateToCounterList(categoryId) },
        onClickCancel = { navigateToCounterList(categoryId) },
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            onValueChange = { onNameChange(it) },
            label = { Text(text = "カウンター名") }
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onClickCancel) {
                Text(text = "キャンセル")
            }

            Spacer(modifier = Modifier.padding(16.dp))

            Button(onClick = onClickAdd) {
                Text(text = "追加する")
            }
        }
    }
}

@HiltViewModel
class AddCounterViewModel @Inject constructor(
    private val counterItemRepository: CounterItemRepository
) : ViewModel() {

    private val _state = MutableStateFlow(initValue)
    val state = _state.asStateFlow()

    fun nameChange(value: String) {
        _state.value = _state.value.copy(name = value)
    }

    fun addCounter(categoryId: Long) {
        val item = CounterItem(
            categoryId = categoryId,
            counterId = UUID.randomUUID().mostSignificantBits,
            counter = 0,
            name = _state.value.name,
            color = _state.value.color,
        )
        counterItemRepository.add(item)
    }
}

data class AddCounterState(
    val name: String,
    val color: Color,
)

val initValue = AddCounterState(
    name = "",
    color = Purple500,
)

@Preview
@Composable
fun Preview() {
    AddCounterScreen(
        categoryId = 1L,
        navigateToCounterList = {},
    )
}