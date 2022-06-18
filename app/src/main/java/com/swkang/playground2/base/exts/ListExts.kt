package com.swkang.playground2.base.exts

/**
 * 주어진 리스트에서 `maxSize`만큼의 원소를 갖는 리스트를 얻으려 한다.
 *
 * - 리스트의 원소가 없을 경우 빈 리스트를 반환 한다.
 * - 리스트의 크기가 주어진 `maxSize`와 같거나 `maxSize`보다 클 경우 리스트를 그대로 얻는다.
 * - 리스트의 크기가 `maxSize`보다 작을 경우 `0`의 인덱스에서 `maxSize`인덱스 까지의 리스트를 얻는다.
 */
fun <E> List<E>.limit(maxSize: Int): List<E> {
    if (maxSize == 0) return emptyList()
    if (this.isEmpty() || this.size == maxSize || this.size < maxSize) return this
    return this.subList(0, maxSize)
}

/**
 * `index`가 리스트에 유효한 인덱스인지 여부를 얻는다.
 */
fun <E> List<E>?.availableIndex(index: Int): Boolean {
    if (index < 0 || this == null || this.isEmpty()) return false
    return index < this.size
}

fun <E> List<E>?.isSafeIndex(index: Int): Boolean = availableIndex(index)

/**
 * `index`에 해당하는 원소를 얻는다.
 * 유효하지 않은 인덱스의 경우 `null`을 반환 한다.
 */
fun <E> List<E>?.get(index: Int): E? {
    if (this.availableIndex(index)) return this.get(index)
    return null
}

/**
 * immutable list에 대해 원소를 추가 한다.
 */
fun <E> List<E>.add(e: E): List<E> {
    val mutableList = this.toMutableList()
    mutableList.add(e)
    return mutableList
}

fun <E> List<E>.addFirst(e: E): List<E> {
    val mutableList = this.toMutableList()
    mutableList.add(0, e)
    return mutableList
}
