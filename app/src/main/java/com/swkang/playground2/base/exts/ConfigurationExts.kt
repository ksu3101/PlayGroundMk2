package com.swkang.playground2.base.exts

import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_LANDSCAPE

fun Configuration.isLandscape(): Boolean =
    orientation == ORIENTATION_LANDSCAPE
