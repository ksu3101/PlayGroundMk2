package com.swkang.playground2.view.main

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swkang.playground2.R
import com.swkang.playground2.theme.DarkBlue30
import com.swkang.playground2.theme.DarkBlue80
import kotlinx.coroutines.launch

@Composable
fun Main(
    onTestGoogleBillingBtn: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    stringResource(id = R.string.app_name)
                },
                contentColor = Color.Black,
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState
                                .drawerState
                                .open()
                        }
                    }) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = "PlayGround"
                        )
                    }
                },
                backgroundColor = DarkBlue80
            )
        },
        drawerContent = {
            Text("Drawer")
        },
        drawerBackgroundColor = Color.White,
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(5.dp))
                MainButton(R.string.btn_title_google_billing, onTestGoogleBillingBtn)
                MainButton(R.string.btn_title_second, onTestGoogleBillingBtn)
                MainButton(R.string.btn_title_third, onTestGoogleBillingBtn)
            }
        }
    )
}

@Composable
private fun MainButton(
    @StringRes text: Int,
    onClicked: () -> Unit
) {
    val shape = RoundedCornerShape(size = 6.dp)
    Button(
        onClick = { onClicked() },
        shape = shape,
        modifier = Modifier.fillMaxWidth()
            .padding(
                horizontal = 12.dp,
                vertical = 6.dp
            )
            .clip(shape)
            .background(DarkBlue30)
    ) {
        Text(
            text = stringResource(text),
            fontSize = 24.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@Preview
@Composable
fun MainPreview() {
    Main({})
}
