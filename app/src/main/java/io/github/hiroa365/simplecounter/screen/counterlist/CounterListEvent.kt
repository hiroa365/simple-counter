package io.github.hiroa365.simplecounter.screen.counterlist

import java.util.*


sealed class CounterListEvent
data class CountUp(val uuid: UUID) : CounterListEvent()
data class CountDown(val uuid: UUID) : CounterListEvent()
data class CountReset(val uuid: UUID) : CounterListEvent()
data class ChangeMode(val mode: CounterMode) : CounterListEvent()
