package io.github.hiroa365.simplecounter.screen.main


data class MainScreenState(
    val counter: Long
) {
    companion object {
        val initValue = MainScreenState(counter = 0)
    }
}
