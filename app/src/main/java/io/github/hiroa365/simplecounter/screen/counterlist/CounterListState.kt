package io.github.hiroa365.simplecounter.screen.counterlist

import java.util.*


data class CounterListState(
    val counterItems: List<CounterItem>,
    val mode: CounterMode
)

//カウンター
data class CounterItem(
    val id: UUID,
    val categoryId: UUID,
    val counter: Int,
    val name: String,
)

sealed class CounterMode {
    object Up : CounterMode()
    object Down : CounterMode()
    object Edit : CounterMode()
}


val categoryId = UUID.randomUUID()
val categoryId2 = UUID.randomUUID()

val initValue = CounterListState(
    counterItems = mutableListOf(
        CounterItem(
            id = UUID.randomUUID(),
            categoryId = categoryId,
            counter = Int.MAX_VALUE,
            name = "項目A"
        ),
        CounterItem(id = UUID.randomUUID(), categoryId = categoryId, counter = 1, name = "項目B"),
        CounterItem(id = UUID.randomUUID(), categoryId = categoryId2, counter = 2, name = "項目C"),
        CounterItem(
            id = UUID.randomUUID(),
            categoryId = categoryId2,
            counter = Int.MAX_VALUE,
            name = "項目D"
        ),
    ),
    mode = CounterMode.Up,
)
