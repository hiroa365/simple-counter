package io.github.hiroa365.simplecounter.screen.counterlist

import io.github.hiroa365.simplecounter.data.repository.CounterItem


data class CounterListState(
    val counterItems: List<CounterItem>,
    val mode: CounterMode
)

sealed class CounterMode {
    object Up : CounterMode()
    object Down : CounterMode()
    object Edit : CounterMode()
}
