package io.github.hiroa365.simplecounter.data.repository

import android.util.Log
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.hiroa365.simplecounter.ui.theme.Purple200
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

interface CounterItemRepository {
    fun getAll(): List<CounterItem>
    fun add(item: CounterItem)
}

class CounterItemRepositoryMock @Inject constructor() : CounterItemRepository {
    private val TAG = javaClass.simpleName

    private val counterItems = arrayListOf(
        CounterItem(
            counterId = UUID.randomUUID().mostSignificantBits,
            categoryId = CategoryItemRepositoryMock.categoryId_1,
            counter = Int.MAX_VALUE,
            name = "項目A",
            color = Purple200,
        ),
        CounterItem(
            counterId = UUID.randomUUID().mostSignificantBits,
            categoryId = CategoryItemRepositoryMock.categoryId_1,
            counter = 1,
            name = "項目B",
            color = Purple200,
        ),
        CounterItem(
            counterId = UUID.randomUUID().mostSignificantBits,
            categoryId = CategoryItemRepositoryMock.categoryId_2,
            counter = 2,
            name = "項目C",
            color = Purple200,
        ),
        CounterItem(
            counterId = UUID.randomUUID().mostSignificantBits,
            categoryId = CategoryItemRepositoryMock.categoryId_2,
            counter = Int.MAX_VALUE,
            name = "項目D",
            color = Purple200,
        ),
    )

    override fun getAll(): List<CounterItem> = counterItems

    override fun add(item: CounterItem) {
        Log.i(TAG, "add $item")
        counterItems.add(item)
    }
}

//カウンター
data class CounterItem(
    /**
     * カテゴリID
     */
    val categoryId: Long,
    /**
     * カウンターID
     */
    val counterId: Long,
    /**
     * カウント値
     */
    val counter: Int,
    /**
     * 名称
     */
    val name: String,
    /**
     * カラー
     */
    val color: Color,
)

@Module
@InstallIn(SingletonComponent::class)
object CounterItemRepositoryModule {

    @Provides
    @Singleton
    fun providerCounterItemRepository(): CounterItemRepository {
        return CounterItemRepositoryMock()
    }
}