package com.swkang.playground2.domain.main

import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.swkang.playground2.R
import com.swkang.playground2.domain.billing.option.PaymentOptionGuide
import com.swkang.playground2.theme.DarkBlue80
import com.swkang.playground2.base.components.DialogButtons
import com.swkang.playground2.base.components.PlayGroundAlertDialog
import kotlinx.coroutines.launch

const val NAV_MAIN = "main"

@Composable
fun Main() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val isDialogShown = remember { mutableStateOf(false) }
    val isGoogleBillingPaymentOptionsGuideShown = remember { mutableStateOf(false) }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
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
            MainDrawer()
        },
        drawerBackgroundColor = Color.White,
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                MainButton(
                    R.string.btn_title_google_billing,
                ) {
                    isGoogleBillingPaymentOptionsGuideShown.value = true
                }
                MainButton(
                    R.string.btn_title_second,
                ) {
                    isDialogShown.value = true
                }
                MainButton(
                    R.string.btn_title_third
                ) { }
            } // column

            // AlertDialog setup
            if (isDialogShown.value) {
                PlayGroundAlertDialog(
                    message = R.string.payment_opt_guide_sub,
                    leftButtonText = R.string.c_no,
                    rightButtonText = R.string.c_confirm,
                    onDismiss = { isDialogShown.value = false },
                    onButtonClicked = {
                        when (it) {
                            DialogButtons.LEFT -> isDialogShown.value = false
                            DialogButtons.RIGHT -> {
                                isDialogShown.value = false
                                Toast.makeText(context, "다이얼로그 -> 확인 버튼 누름", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                )
            } // if (isDialogShown.value)
        }
    ) // scaffold

    // Google Billing payment option guide
    AnimatedVisibility(
        visible = isGoogleBillingPaymentOptionsGuideShown.value,
        enter = fadeIn(
            initialAlpha = 0.0f
        ),
        exit = fadeOut(
            animationSpec = tween(durationMillis = 200)
        )
    ) {
        PaymentOptionGuide(
            {
                isGoogleBillingPaymentOptionsGuideShown.value = false
                Log.d("MainActivity", "구글결제 - 결제 옵션 화면 -> 배경 딤 누름")
            },
            {
                isGoogleBillingPaymentOptionsGuideShown.value = false
                Toast.makeText(context, "구글결제 - 결제 옵션 화면 -> 자세히 알아보기 누름", Toast.LENGTH_SHORT).show()
            },
            {
                isGoogleBillingPaymentOptionsGuideShown.value = false
                Toast.makeText(context, "구글결제 - 결제 옵션 화면 -> 계속 누름", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

@Preview
@Composable
fun MainPreview() {
    Main()
}

@Composable
private fun MainButton(
    @StringRes text: Int,
    onClicked: () -> Unit
) {
    val roundedCornerShape = RoundedCornerShape(size = 10.dp)
    Button(
        onClick = { onClicked() },
        shape = roundedCornerShape,
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 12.dp)
            .clip(roundedCornerShape)
    ) {
        Text(
            text = stringResource(text),
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@Preview
@Composable
private fun MainDrawer() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Text("Drawer")
    }
}
