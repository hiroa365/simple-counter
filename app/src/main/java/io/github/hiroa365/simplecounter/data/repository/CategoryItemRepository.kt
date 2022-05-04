package io.github.hiroa365.simplecounter.data.repository

import android.util.Log
import androidx.compose.ui.graphics.Color
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.hiroa365.simplecounter.ui.theme.Purple200
import io.github.hiroa365.simplecounter.ui.theme.Purple500
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

interface CategoryItemRepository {
    fun getAll(): List<CategoryItem>
    fun add(item: CategoryItem)
}


class CategoryItemRepositoryMock @Inject constructor() : CategoryItemRepository {

    companion object {
        private val TAG = javaClass.simpleName

        val categoryId_1 = UUID.randomUUID().mostSignificantBits
        val categoryId_2 = UUID.randomUUID().mostSignificantBits
    }

    private val categoryItems = arrayListOf(
        CategoryItem(categoryId = categoryId_1, name = "カテゴリ１", color = Purple500),
        CategoryItem(categoryId = categoryId_2, name = "カテゴリ２", color = Purple500),
    )

    override fun getAll(): List<CategoryItem> = categoryItems

    override fun add(item: CategoryItem) {
        Log.i(TAG, "add $item")
        categoryItems.add(item)
    }

}

data class CategoryItem(
    /**
     * カテゴリーID
     */
    val categoryId: Long,
    /**
     * カテゴリ名
     */
    val name: String,
    /**
     * 色
     */
    val color: Color
)

@Module
@InstallIn(SingletonComponent::class)
object CategoryItemRepositoryModule {

    @Provides
    @Singleton
    fun provideCategoryItemRepository(): CategoryItemRepository {
        return CategoryItemRepositoryMock()
    }
}
