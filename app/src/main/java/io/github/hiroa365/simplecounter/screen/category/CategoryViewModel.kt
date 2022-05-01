package io.github.hiroa365.simplecounter.screen.category

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


class CategoryViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(initValue)
    val state = _state.asStateFlow()
}