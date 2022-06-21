package com.swkang.playground2.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swkang.playground2.theme.PlayGroundTheme

@Composable
fun PlayGroundAlertDialog(
    message: String,
    buttonText: String,
    onDismiss: () -> Unit,
    onButtonClicked: () -> Unit
) {
    PlayGroundTheme {
        AlertDialog(
            onDismissRequest = onDismiss,
            text = { Text(message) },
            buttons = {
                Column {
                    Divider(
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                    TextButton(
                        onClick = onButtonClicked,
                        shape = RectangleShape,
                        contentPadding = PaddingValues(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(buttonText)
                    }
                }
            }
        )
    }
}

@Preview
@Composable
private fun PlayGroundALertDialogPreview() {
    PlayGroundAlertDialog(
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",
        "확인",
        {},
        {}
    )
}
