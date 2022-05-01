package io.github.hiroa365.simplecounter.screen.main

import java.util.*


sealed class MainScreenEvent
data class CountUp(val uuid: UUID) : MainScreenEvent()
data class CountDown(val uuid: UUID) : MainScreenEvent()
data class CountReset(val uuid: UUID) : MainScreenEvent()
data class ChangeMode(val mode: CounterMode) : MainScreenEvent()
