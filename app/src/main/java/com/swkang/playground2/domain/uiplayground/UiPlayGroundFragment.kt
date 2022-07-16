package com.swkang.playground2.domain.uiplayground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.swkang.model.domain.uiplayground.UiPlayGroundFragViewModel
import com.swkang.playground2.base.BaseFragment
import com.swkang.playground2.theme.PlayGroundTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author burkdog
 */
@AndroidEntryPoint
class UiPlayGroundFragment : BaseFragment() {
    private val viewModel: UiPlayGroundFragViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                PlayGroundTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        UiPlayGround(viewModel)
                    }
                }
            }
        }
    }
}
