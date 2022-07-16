package com.swkang.playground2.domain.uiplayground

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swkang.model.domain.uiplayground.UiPlayGroundFragViewModel
import com.swkang.playground2.R
import com.swkang.playground2.base.components.BounceButton
import com.swkang.playground2.theme.DarkBlue20

private const val TAG = "UiPlayGround"

@Composable
fun UiPlayGround(viewModel: UiPlayGroundFragViewModel) {
    val context = LocalContext.current
    val state = viewModel.uiPlayGroundState.collectAsState().value
    Log.d(TAG, ">> uistate received : ${state::class.simpleName}")
    when (state) {
        is UiPlayGroundState.Initialized -> {
            // 최초 상태 : 화면 입력란 등 모두 지우고 초기화
            PlayGroundContents(
                onClickedLoginBtn = {
                    viewModel.requestLogin("asdf", "zxxcv")
                }
            )
        }
        is UiPlayGroundState.LoginSuccess -> {
            // TODO - 로그인 성공 : 완료 토스트 보여주고 예제 데이터를 같이 화면에 보여주면 될듯?
        }
        is UiPlayGroundState.Error.Common -> {
            // 공통 오류 : 일단 토스트 출력
            Toast.makeText(context, stringResource(id = R.string.uipg_login_failed), Toast.LENGTH_SHORT).show()
        }
        else -> Log.w(TAG, "un handled state. [$state]")
    }
}

@Composable
private fun PlayGroundContents(
    onClickedLoginBtn: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        BounceButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 12.dp)
                .clip(RoundedCornerShape(size = 10.dp))
                .background(DarkBlue20),
            onClick = onClickedLoginBtn
        ) { scale ->
            Text(
                text = "Bounce Button",
                fontSize = 24.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
            )
        }
    }
}
