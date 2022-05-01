package io.github.hiroa365.simplecounter.screen.main

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


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
