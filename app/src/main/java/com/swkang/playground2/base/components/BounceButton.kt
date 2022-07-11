package com.swkang.playground2.base.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class BounceButtonState {
    Pressed,
    Released
}

@Composable
fun BounceButton(
    modifier: Modifier,
    onClick: () -> Unit,
    content: @Composable BoxScope.(scale: Float) -> Unit
) {
    var currentState: BounceButtonState by remember { mutableStateOf(BounceButtonState.Released) }
    val transition = updateTransition(targetState = currentState, label = "transition_animation")
    val scale: Float by transition.animateFloat(
        transitionSpec = {
            spring(stiffness = 900f)
        },
        label = ""
    ) { state ->
        if (state == BounceButtonState.Pressed) {
            0.9f
        } else {
            1f
        }
    }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        currentState = BounceButtonState.Pressed
                        tryAwaitRelease()
                        currentState = BounceButtonState.Released
                        onClick()
                    }
                )
            }
        ) {
            content(scale)
        }
    }
}

@Preview
@Composable
fun PreviewBounceButton() {
    BounceButton(
        onClick = {},
        modifier = Modifier.width(220.dp)
            .height(60.dp)
    ) {
        Text(
            text = "Bounce Button",
            fontSize = 20.sp
        )
    }
}
