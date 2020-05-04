package com.teutit.games.game2048

/**
 * This function moves all non-null elements to the beginning of the list
 * (by removing null elements) and merges equal elements.
 * The parameter merge specifies how to merge equal elements:
 * it returns a new element that should be presented in the resulting list
 * instead of the equal/merged elements.
 *
 * If the function 'merge("a")' returns "aa",
 * then the function 'moveAndMergeEqual' transforms the input in the following ways:
 *   a, a, b -> aa, b
 *   a, 0-> a
 *   b, 0, a, a -> b, aa
 *   a, a, 0, a -> aa, a
 *   a, 0, a, a -> a, aa
 */

fun <T: Any>List<T?>.moveAndMergeEqual(mergeFunction: (T) -> T): List<T> {
    val nonNullValues = this.filterNotNull()
    if (nonNullValues.isEmpty()) return emptyList()

    return nonNullValues.resolveGroup(mergeFunction)
}

private fun <T>List<T>.resolveGroup(mergeFunction: (T) -> T): List<T> {
    if (this.isEmpty()) return emptyList()
    val group = this.takeWhile { it == this[0] }

    val resolvedGroup = when (group.size) {
        2 -> mutableListOf(mergeFunction(group[0]))
        3 -> mutableListOf(mergeFunction(group[0]), group[0])
        4 -> mutableListOf(mergeFunction(group[0]), group[0], group[0])
        else -> mutableListOf(group[0])
    }

    val tail = this.takeLast(this.size - group.size)

    return mutableListOf(resolvedGroup, tail.resolveGroup(mergeFunction)).flatten()
}