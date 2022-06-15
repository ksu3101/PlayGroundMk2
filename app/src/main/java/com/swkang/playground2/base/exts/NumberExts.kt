package com.swkang.playground2.base.exts

/**
 * 숫자를 ###,### 패턴의 숫자로 변경 해 준다.
 *
 * - null or 0 : 0
 * - Long, Integer : 000,000
 * - Double, Float : 000,000.000
 */
fun Number?.toCommaNumberString(): String {
    if (this == null || this == 0) return "0"
    if (this is Double) return "%,f".format(this)
    return "%,d".format(this)
}