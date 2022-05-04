package io.github.hiroa365.simplecounter.screen.counterlist

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.hiroa365.simplecounter.data.repository.CounterItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class CounterListViewModel @Inject constructor(
    counterItemRepository: CounterItemRepository
) : ViewModel() {
    private val TAG = javaClass.simpleName


    private val initValue = CounterListState(
        counterItems = counterItemRepository.getAll(),
        mode = CounterMode.Up,
    )


    private val _state: MutableStateFlow<CounterListState> = MutableStateFlow(initValue)
    val state: StateFlow<CounterListState> = _state.asStateFlow()

    fun sendEvent(event: CounterListEvent) {
        when (event) {
            is CountUp -> {
                Log.i(TAG, "count up uuid:$event")
                val newCounterItems = _state.value.counterItems.map {
                    if (it.counterId == event.id && it.counter < Int.MAX_VALUE) {
                        it.copy(counter = it.counter + 1)
                    } else it
                }
                _state.value = _state.value.copy(counterItems = newCounterItems)
            }
            is CountDown -> {
                Log.i(TAG, "count down uuid:$event")
                val newCounterItems = _state.value.counterItems.map {
                    if (it.counterId == event.id && it.counter > 0) {
                        it.copy(counter = it.counter - 1)
                    } else it
                }
                _state.value = _state.value.copy(counterItems = newCounterItems)
            }
            is CountReset -> {
                Log.i(TAG, "count reset uuid:$event")
                val newCounterItems = _state.value.counterItems.map {
                    if (it.counterId == event.id) it.copy(counter = 0)
                    else it
                }
                _state.value = _state.value.copy(counterItems = newCounterItems)
            }
            is ChangeMode -> {
                Log.i(TAG, "change mode:${event.id}")
                _state.value = _state.value.copy(mode = event.id)
            }
        }
    }
}
