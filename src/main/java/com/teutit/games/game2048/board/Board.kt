package com.teutit.games.game2048.board

data class Cell(val i: Int, val j: Int) {
    override fun toString(): String {
        return "($i, $j)"
    }
}

enum class Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN
}

interface SquareBoard {

    operator fun get(i: Int, j: Int): Cell
    fun getAllCells(): Collection<Cell>
    fun getRow(i: Int, jRange: IntProgression): List<Cell>
    fun getColumn(iRange: IntProgression, j: Int): List<Cell>
}

interface GameBoard : SquareBoard {

    fun filter(predicate: (Int?) -> Boolean): Set<Cell>
    fun any(predicate: (Int?) -> Boolean): Boolean
    operator fun set(cell: Cell, value: Int?)
    operator fun get(cell: Cell): Int?
}