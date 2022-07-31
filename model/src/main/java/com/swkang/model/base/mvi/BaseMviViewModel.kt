package com.swkang.model.base.mvi

import com.swkang.model.base.BaseViewModel

/**
 * @author burkdog
 */
abstract class BaseMviViewModel<A : Action, S : State> : BaseViewModel() {
    abstract val model: BaseModel<A, S>

    fun launchAction(action: A) {
    }

    abstract fun render(state: S)
}
