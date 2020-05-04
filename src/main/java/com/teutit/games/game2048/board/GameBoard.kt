package com.teutit.games.game2048.board

fun createSquareBoard(width: Int) = SquareBoardImpl(width)
fun createGameBoard(width: Int) = GameBoardImpl(createSquareBoard(width))

class SquareBoardImpl(private val width: Int): SquareBoard {

    private val matrix = run {
        var matrix = arrayOf<Array<Cell>>()
        for (i in 0 until width) {
            var row = arrayOf<Cell>()
            for (j in 0 until width) {
                row += Cell(i, j)
            }
            matrix += row
        }
        matrix
    }

    override operator fun get(i: Int, j: Int): Cell = matrix[i][j]

    override fun getAllCells(): Collection<Cell> {
        return matrix.toList().flatMap { it.toList() }
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val result = mutableListOf<Cell>()
        val row = matrix[i]
        for (j in jRange) {
            result.add(row[j])
        }
        return result
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val result = mutableListOf<Cell>()

        for (i in iRange) {
            val row = matrix[i]
            result.add(row[j])
        }
        return result
    }
}


class GameBoardImpl(squareBoard: SquareBoard): GameBoard, SquareBoard by squareBoard {

    private val valuesMap = run {
        val valuesMap = mutableMapOf<Cell, Int?>()
        squareBoard.getAllCells().forEach { valuesMap[it] = null }
        valuesMap
    }

    override fun filter(predicate: (Int?) -> Boolean): Set<Cell> = valuesMap.filterValues(predicate).keys

    override fun any(predicate: (Int?) -> Boolean): Boolean = valuesMap.values.any(predicate)

    override operator fun set(cell: Cell, value: Int?) {
        valuesMap[cell] = value
    }

    override operator fun get(cell: Cell): Int? = valuesMap[cell]

}