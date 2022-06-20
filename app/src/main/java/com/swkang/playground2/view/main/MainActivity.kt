package com.swkang.playground2.view.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.swkang.playground2.theme.PlayGroundTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlayGroundTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Main {
                        // handle click events.
                        Log.d(TAG, "onClicked : ${it.name}")
                        when (it) {
                            MainButton.GOOGLE_BILLING -> {
                                // todo : 구글 결제 화면 이동
                            }
                            MainButton.SECOND -> {}
                            MainButton.THIRD -> {}
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
