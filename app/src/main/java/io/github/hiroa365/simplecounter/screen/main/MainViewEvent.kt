package io.github.hiroa365.simplecounter.screen.main


sealed class MainViewEvent
object OnEventCountUp : MainViewEvent()
object OnEventCountClear : MainViewEvent()
