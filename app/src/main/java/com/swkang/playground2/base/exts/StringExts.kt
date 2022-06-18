package com.swkang.playground2.base.exts

/**
 * 주어진 문자열이 숫자로만 이루어져 있는지 확인 한다.
 *
 * - null or "" : false
 * - "123456" : true
 * - "123.456" : false
 * - "abc123" : false
 */
fun String?.isNumber(): Boolean {
    return if (this.isNullOrEmpty()) false
    else this.matches("\\d+".toRegex())
}

/**
 * 주어진 문자열이 -, +기호가 포함된 숫자로 이루어져 있는지 확인 한다.
 *
 * - null or "" : false
 * - "123456" : true
 * - "123.456" : false
 * - "abc123" : false
 * - "-123" : true
 */
fun String?.isSignedNumber(): Boolean {
    return if (this.isNullOrEmpty()) false
    else this.matches("[0-9+#-]+".toRegex())
}
