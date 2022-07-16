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
import androidx.compose.runtime.MutableState
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
import com.swkang.playground2.base.components.DialogButtons
import com.swkang.playground2.base.components.PlayGroundAlertDialog
import com.swkang.playground2.domain.billing.option.NextSelectPaymentMethods
import com.swkang.playground2.domain.billing.option.OnPaymentMethodClicked
import com.swkang.playground2.domain.billing.option.PaymentOptionGuide
import com.swkang.playground2.domain.billing.option.SelectGoogleInAppPurchase
import com.swkang.playground2.domain.billing.option.SelectPaymentMethod
import com.swkang.playground2.domain.billing.option.SelectThirdPartyMethod
import com.swkang.playground2.theme.DarkBlue80
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
    val isGoogleBillingSelectPaymentGuideShown = remember { mutableStateOf(false) }
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
            } // column
        } // content { }
    ) // scaffold

    // 테스트용 얼럿 다이얼로그
    if (isDialogShown.value) {
        PlayGroundAlertDialog {
            isDialogShown.value = false
        }
    }

    // Google Billing payment option guide
    PaymentOptionGuide(
        isGoogleBillingPaymentOptionsGuideShown,
        isGoogleBillingSelectPaymentGuideShown
    )

    // Google Billing select payment method page
    SelectPaymentMethod(isGoogleBillingSelectPaymentGuideShown)
}

@Preview
@Composable
fun MainPreview() {
    Main()
}

@Composable
private fun PaymentOptionGuide(
    isPaymentOptGuideVisible: MutableState<Boolean>,
    isSelectPaymentMethod: MutableState<Boolean>
) {
    AnimatedVisibility(
        visible = isPaymentOptGuideVisible.value,
        enter = fadeIn(
            initialAlpha = 0.0f
        ),
        exit = fadeOut(
            animationSpec = tween(durationMillis = 200)
        )
    ) {
        PaymentOptionGuide { clicked ->
            isPaymentOptGuideVisible.value = false
            when (clicked) {
                OnPaymentMethodClicked.NeedMoreInfos -> {
                    // todo : 외부 브라우저 이동 (https://support.google.com/googleplay/answer/11174377?hl=ko)
                }
                NextSelectPaymentMethods -> {
                    // 결제 방법 선택 화면 등장
                    isSelectPaymentMethod.value = true
                }
                else -> Log.d("Main", "none handled method [$clicked]")
            }
        }
    }
}

@Composable
private fun SelectPaymentMethod(isSelectPaymentMethod: MutableState<Boolean>) {
    AnimatedVisibility(
        visible = isSelectPaymentMethod.value,
        enter = fadeIn(
            initialAlpha = 0.0f
        ),
        exit = fadeOut(
            animationSpec = tween(durationMillis = 200)
        )
    ) {
        SelectPaymentMethod { clicked ->
            isSelectPaymentMethod.value = false
            when (clicked) {
                OnPaymentMethodClicked.NeedMoreInfos -> {
                    // todo : 외부 브라우저 이동 (https://support.google.com/googleplay/answer/11174377?hl=ko)
                }
                SelectThirdPartyMethod -> {
                    // todo : 외부 결제 화면 이동
                }
                SelectGoogleInAppPurchase -> {
                    // todo : 구글 인앱 결제 흐름 프로세스 진행
                }
                else -> Log.d("Main", "none handled method [$clicked]")
            }
        }
    }
}

@Composable
private fun PlayGroundAlertDialog(onDismiss: () -> Unit) {
    val context = LocalContext.current
    PlayGroundAlertDialog(
        message = R.string.payment_opt_guide_sub,
        leftButtonText = R.string.c_no,
        rightButtonText = R.string.c_confirm,
        onDismiss = onDismiss,
        onButtonClicked = {
            when (it) {
                DialogButtons.LEFT -> onDismiss()
                DialogButtons.RIGHT -> {
                    onDismiss()
                    Toast.makeText(context, "다이얼로그 -> 확인 버튼 누름", Toast.LENGTH_SHORT).show()
                }
            }
        }
    )
}

@Composable
private fun MainButton(
    @StringRes text: Int,
    onClicked: () -> Unit
) {
    val roundedCornerShape = RoundedCornerShape(size = 10.dp)
    Button(
        onClick = onClicked,
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
