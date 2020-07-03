package com.teutit.game.board

enum class Direction {
    LEFT, RIGHT, UP, DOWN
}

data class Cell(val i: Int, val j: Int) {
    override fun toString(): String = "($i, $j)"
}

interface SquareBoard {
    fun getAllCells(): Collection<Cell>
    operator fun get(i: Int, j: Int): Cell
    fun getRow(i: Int, intRange: IntProgression): Collection<Cell>
    fun getColumn(iRange: IntProgression, j: Int): Collection<Cell>
}

interface GameBoard: SquareBoard {
    operator fun get(cell: Cell): Int?
    operator fun set(cell: Cell, value: Int?)
    fun filter(predicate: (Int?) -> Boolean): Collection<Cell>
    fun any(predicate: (Int?) -> Boolean): Boolean
}