package com.swkang.playground2.view.components

import android.util.Log
import androidx.annotation.FloatRange
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.swkang.playground2.R
import com.swkang.playground2.theme.PlayGroundTheme

enum class DialogButtons {
    LEFT, RIGHT
}

private data class AlertDialogButton(
    val btnText: String,
    val btnType: DialogButtons,
    @FloatRange(from = 0.0, to = 1.0) val fillMaxWidthFraction: Float
)

@Composable
fun PlayGroundAlertDialog(
    @StringRes title: Int? = null,
    @StringRes message: Int,
    @StringRes leftButtonText: Int,
    @StringRes rightButtonText: Int? = null,
    properties: DialogProperties = DialogProperties(),
    onDismiss: () -> Unit,
    onButtonClicked: (DialogButtons) -> Unit
) {
    PlayGroundAlertDialog(
        title = if (title != null) stringResource(title) else "",
        message = stringResource(message),
        leftButtonText = stringResource(leftButtonText),
        rightButtonText = if (rightButtonText != null) stringResource(rightButtonText) else "",
        properties = properties,
        onDismiss = onDismiss,
        onButtonClicked = onButtonClicked
    )
}

@Composable
fun PlayGroundAlertDialog(
    title: String = "",
    message: String,
    leftButtonText: String,
    rightButtonText: String = "",
    properties: DialogProperties = DialogProperties(),
    onDismiss: () -> Unit,
    onButtonClicked: (DialogButtons) -> Unit
) {
    val isRightBtnVisible = rightButtonText.isNotEmpty()
    val buttons = mutableListOf(
        AlertDialogButton(
            leftButtonText,
            DialogButtons.LEFT,
            if (!isRightBtnVisible) 1.0f else 0.5f
        )
    )
    if (isRightBtnVisible) {
        buttons.add(
            AlertDialogButton(
                rightButtonText,
                DialogButtons.RIGHT,
                0.5f
            )
        )
    }
    PlayGroundTheme {
        AlertDialog(
            title = if (title.isNotEmpty()) { { Text(title) } } else null,
            onDismissRequest = onDismiss,
            text = { Text(message) },
            properties = properties,
            buttons = {
                Column {
                    Divider(modifier = Modifier.padding(horizontal = 10.dp))
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        // contentPadding = PaddingValues(vertical = 4.dp)
                    ) {
                        items(items = buttons, itemContent = { item ->
                            PlayGroundDialogButton(
                                btnText = item.btnText,
                                modifier = Modifier.fillParentMaxWidth(item.fillMaxWidthFraction)
                            ) {
                                onButtonClicked(item.btnType)
                            }
                        })
                    }
                }
            }
        )
    }
}

@Composable
private fun PlayGroundDialogButton(
    modifier: Modifier = Modifier,
    btnText: String,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        shape = RectangleShape,
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
    ) {
        Text(
            text = btnText,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun PlayGroundALertDialogSingleBtnPreview() {
    PlayGroundAlertDialog(
        message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",
        leftButtonText = "취소",
        onDismiss = { },
        onButtonClicked = { Log.d("PlayGroundAlertDialog", "$it Clicked.") }
    )
}

@Preview
@Composable
private fun PlayGroundALertDialogTwoBtnPreview() {
    PlayGroundAlertDialog(
        title = "타이틀 문구",
        message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",
        leftButtonText = "취소",
        rightButtonText = "확인",
        onDismiss = { },
        onButtonClicked = { Log.d("PlayGroundAlertDialog", "$it Clicked.") }
    )
}

@Preview
@Composable
private fun PlayGroundALertDialogFromStringResources() {
    PlayGroundAlertDialog(
        message = R.string.payment_opt_guide_sub,
        leftButtonText = R.string.c_no,
        rightButtonText = R.string.c_confirm,
        onDismiss = { },
        onButtonClicked = { Log.d("PlayGroundAlertDialog", "$it Clicked.") }
    )
}
