package com.swkang.model.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * @author burkd
 */
class BaseViewModel: ViewModel() {

    protected infix fun <T> MutableSharedFlow<T>.emmitting(data: T) {
        viewModelScope.launch {
            emit(data)
        }
    }
}