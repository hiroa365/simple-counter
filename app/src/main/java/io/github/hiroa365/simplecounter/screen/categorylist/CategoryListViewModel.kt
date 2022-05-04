package io.github.hiroa365.simplecounter.screen.categorylist

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.hiroa365.simplecounter.data.repository.CategoryItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CategoryListViewModel @Inject constructor(
    categoryItemRepository: CategoryItemRepository
) : ViewModel() {

    private val initValue = CategoryListScreenState(
        categoryList = categoryItemRepository.getAll()
    )


    private val _state = MutableStateFlow(initValue)
    val state = _state.asStateFlow()

}
