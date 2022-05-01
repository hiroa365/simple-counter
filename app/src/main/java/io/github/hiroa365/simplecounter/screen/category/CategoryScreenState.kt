package io.github.hiroa365.simplecounter.screen.category

import java.util.*


data class CategoryScreenState(
    val categoryList: List<CategoryItem>,
)

data class CategoryItem(
    val id: UUID,
    val name: String,
)


val initValue = CategoryScreenState(
    categoryList = listOf(
        CategoryItem(id = UUID.randomUUID(), name = "カテゴリ１"),
        CategoryItem(id = UUID.randomUUID(), name = "カテゴリ２"),
    )
)
