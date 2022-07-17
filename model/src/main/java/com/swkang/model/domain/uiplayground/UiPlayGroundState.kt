package com.swkang.playground2.domain.uiplayground

/**
 * ui play ground states
 */
sealed class UiPlayGroundState {
    object Initialized : UiPlayGroundState()
    data class LoginSuccess(
        val id: String,
        val userData: UserData
    ) : UiPlayGroundState()

    sealed class Error : UiPlayGroundState() {
        data class Common(
            val errorMsg: String,
            val throwable: Throwable
        ) : Error()

        object EmptyInputField : Error()
        data class LoginFailed(
            val errorMsg: String = ""
        ) : Error()
    }
}

/**
 * 로그인 한 사용자의 정보 (테스트 및 예제를 위해 간결화 함)
 */
data class UserData(
    val userName: String,
    val eMail: String,
    val age: Int
    // ...
)
