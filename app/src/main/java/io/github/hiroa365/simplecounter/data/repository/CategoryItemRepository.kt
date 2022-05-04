package io.github.hiroa365.simplecounter.data.repository

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
        CategoryItem(categoryId = categoryId_1, name = "カテゴリ１"),
        CategoryItem(categoryId = categoryId_2, name = "カテゴリ２"),
    )

    override fun getAll(): List<CategoryItem> = categoryItems

    override fun add(item: CategoryItem) {
        Log.i(TAG, "add $item")
        categoryItems.add(item)
    }

}

data class CategoryItem(
    val categoryId: Long,
    val name: String,
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
