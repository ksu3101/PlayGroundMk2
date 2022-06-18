package com.swkang.playground2.base.exts

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

@ColorInt
fun Context.getColorByCompat(@ColorRes resId: Int): Int {
    return ContextCompat.getColor(this, resId)
}
