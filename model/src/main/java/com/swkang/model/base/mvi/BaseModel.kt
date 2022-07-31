package com.swkang.model.base.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * @author burkdog
 */
abstract class BaseModel<A : Action, S : State> {
    private val _statePublisher = MutableStateFlow(Initialzed as S)
    val statePublisher = _statePublisher.asStateFlow()

    abstract fun actionProcessor(action: A): A

    abstract fun reducer(state: S, action: A): S

    private infix fun <T> Flow<T>.emitting(value: T) {
        flow<T> { emit(value) }.flowOn(IoDispatcher)
    }
}
