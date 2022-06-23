package com.swkang.model.base.helper

interface ResourceHelper {
    fun getString(strResId: Int): String

    fun getString(strResId: Int, vararg args: Any?): String

    fun convertToDp(px: Float): Float

    fun convertToPx(dip: Float): Float
}
