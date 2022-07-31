package com.swkang.playground2.base.mvi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import com.swkang.model.base.mvi.BaseMviViewModel
import com.swkang.model.base.mvi.State
import com.swkang.playground2.base.BaseFragment
import com.swkang.playground2.theme.PlayGroundTheme

/**
 * @author burkdog
 */
abstract class BaseMviFragment<S : State> : BaseFragment() {
    abstract val viewModel: BaseMviViewModel<S>

    abstract fun contentView(): @Composable () -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                PlayGroundTheme {
                    contentView()
                }
            }
        }
    }
}
