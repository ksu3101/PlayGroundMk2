package com.swkang.model.domain.uiplayground

import androidx.lifecycle.viewModelScope
import com.swkang.model.base.BaseViewModel
import com.swkang.model.base.helper.ResourceHelper
import com.swkang.playground2.domain.uiplayground.UiPlayGroundState
import com.swkang.playground2.domain.uiplayground.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

/**
 * @author burkdog
 */
@HiltViewModel
class UiPlayGroundFragViewModel @Inject constructor(
    val resourceHelper: ResourceHelper
) : BaseViewModel() {
    private val _uiPlayGroundState = MutableStateFlow<UiPlayGroundState>(UiPlayGroundState.Initialized)
    val uiPlayGroundState = _uiPlayGroundState.asStateFlow()

    init {
        // 이거 필요한걸까?
        _uiPlayGroundState emmitting UiPlayGroundState.Initialized
    }

    fun requestLogin(
        id: String,
        pw: String
    ) {
        try {
            viewModelScope.launch {
                // 비동기 작업을 하는것 처럼 딜레이를 준다.
                // 원래는 여기에서 use-case나 Repository, local db 등 비동기 작업을 진행 한다.
                delay(1200L)
                // 진행 후 결과에 따라 state를 만들고 내보낸다.
                _uiPlayGroundState emmitting UiPlayGroundState.LoginSuccess(
                    id,
                    UserData("username", "bbb@google.com", 26)
                )
            }
        } catch (e: Exception) {
            _uiPlayGroundState emmitting UiPlayGroundState.Error.LoginFailed(e.message ?: "")
        }
    }
}
