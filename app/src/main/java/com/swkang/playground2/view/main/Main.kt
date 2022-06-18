package com.swkang.playground2.view.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swkang.playground2.R
import com.swkang.playground2.theme.Blue40
import com.swkang.playground2.theme.PlayGroundTheme
import com.swkang.playground2.theme.Red40
import com.swkang.playground2.theme.Yellow80
import kotlinx.coroutines.launch

@Composable
fun Main() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val colors = arrayListOf(
        Blue40,
        Yellow80,
        Red40
    )
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    stringResource(id = R.string.app_name)
                },
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
                backgroundColor = Color.Blue
            )
        },
        drawerContent = {
            Text("Drawer")
        },
        drawerBackgroundColor = Color.White,
        content = { innerPadding ->
            LazyColumn(contentPadding = innerPadding) {
                items(count = 10) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .background(colors[it % colors.size])
                    )
                }
            }
        }
    )
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PlayGroundTheme {
        Greeting("Android")
    }
}
