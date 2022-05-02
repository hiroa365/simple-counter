package io.github.hiroa365.simplecounter.screen.categorylist

import java.util.*


data class CategoryListScreenState(
    val categoryList: List<CategoryItem>,
)

data class CategoryItem(
    val id: UUID,
    val name: String,
)


val initValue = CategoryListScreenState(
    categoryList = listOf(
        CategoryItem(id = UUID.randomUUID(), name = "カテゴリ１"),
        CategoryItem(id = UUID.randomUUID(), name = "カテゴリ２"),
    )
)
