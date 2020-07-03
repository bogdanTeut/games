package com.teutit.game.games.game2048

fun <T : Any>Collection<T?>.moveAndMerge(mergeFunction: (T) -> T): List<T?> {
    val nonNullValues = this.filterNotNull()
    return nonNullValues.findGroupAndMerge(mergeFunction)
}

private fun <T : Any> List<T>.findGroupAndMerge(mergeFunction: (T) -> T): List<T?> {
    if (this.isEmpty()) return emptyList()

    val group = this.takeWhile { it == this.first() }
    val groupResolved = group.merge(mergeFunction)
    val tail = this.takeLast(this.size - group.size)

    return mutableListOf(groupResolved, tail.moveAndMerge(mergeFunction)).flatten()
}

fun <T : Any>List<T>.merge(mergeFunction: (T) -> T): List<T> =
    when (this.size) {
        2 -> mutableListOf(mergeFunction(this.first()))
        3 -> mutableListOf(mergeFunction(this.first()), this.first())
        4 -> mutableListOf(mergeFunction(this.first()), this.first(), this.first())
        else -> this
    }
