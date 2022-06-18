package com.swkang.playground2.base.helper

import android.content.Context

class ResourceHelperImpl(
    private val context: Context
) : ResourceHelper {
    override fun getString(strResId: Int): String = context.getString(strResId)

    override fun getString(strResId: Int, vararg args: Any?): String =
        context.getString(strResId, *args)

    override fun convertToDp(px: Float): Float =
        px / context.resources.displayMetrics.density

    override fun convertToPx(dip: Float): Float =
        dip * context.resources.displayMetrics.density
}
