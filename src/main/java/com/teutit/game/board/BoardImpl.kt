package com.teutit.game.board

fun createSquareBoard(width: Int) = SquareBoardImpl(width)
fun createGameBoard(squareBoard: SquareBoard) = GameBoardImpl(squareBoard)

class SquareBoardImpl(width: Int) : SquareBoard {
    private var matrix = run {
        var result = arrayOf<Array<Cell>>()
        (0 until width).forEach { row ->
            var array = arrayOf<Cell>()
            (0 until width).forEach { column ->
                array += Cell(row, column)
            }
            result += array
        }
        result
    }

    override fun getAllCells(): Collection<Cell> = matrix.toList().flatMap { it.toList() }

    override operator fun get(i: Int, j: Int): Cell = matrix[i][j]
    override fun getRow(i: Int, jRange: IntProgression): Collection<Cell> = run {
            val result = mutableListOf<Cell>()
            for (j in jRange) {
                result.add(matrix[i][j])
            }
            result
        }

    override fun getColumn(iRange: IntProgression, j: Int): Collection<Cell> = run {
        val result = mutableListOf<Cell>()
        for (i in iRange) {
            result.add(matrix[i][j])
        }
        result
    }

}

class GameBoardImpl(squareBoard: SquareBoard): GameBoard, SquareBoard by squareBoard {
    private var valuesMap: MutableMap<Cell, Int?> = squareBoard.getAllCells().map { it to null }.toMap().toMutableMap()

    override operator fun get(cell: Cell): Int? = valuesMap[cell]

    override fun set(cell: Cell, value: Int?) {
        valuesMap[cell] = value
    }

    override fun filter(predicate: (Int?) -> Boolean): Collection<Cell> = valuesMap.filterValues(predicate).keys

    override fun any(predicate: (Int?) -> Boolean): Boolean = valuesMap.values.any(predicate)
}