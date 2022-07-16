package com.swkang.model.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * @author burkdog
 */
abstract class BaseViewModel : ViewModel() {

    protected infix fun <T> MutableStateFlow<T>.emmitting(data: T) {
        viewModelScope.launch {
            emit(data)
        }
    }

    protected infix fun <T> MutableSharedFlow<T>.emmitting(data: T) {
        viewModelScope.launch {
            emit(data)
        }
    }
}
