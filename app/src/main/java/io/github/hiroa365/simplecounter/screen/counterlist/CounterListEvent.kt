package io.github.hiroa365.simplecounter.screen.counterlist

import java.util.*


sealed class CounterListEvent
data class CountUp(val id: Long) : CounterListEvent()
data class CountDown(val id: Long) : CounterListEvent()
data class CountReset(val id: Long) : CounterListEvent()
data class ChangeMode(val id: CounterMode) : CounterListEvent()
