package com.teutit.games.game2048

import com.teutit.games.game2048.board.Cell
import com.teutit.games.game2048.board.Direction
import com.teutit.games.game2048.board.GameBoard
import com.teutit.games.game2048.board.createGameBoard

fun createGame2048(width: Int, valueGenerator: Game2048ValueGenerator = RandomGame2048ValueGenerator): Game2048 = Game2048(width, valueGenerator)

class Game2048(private val width: Int, private val valueGenerator: Game2048ValueGenerator) : Game {

    private val gameBoard = createGameBoard(4)

    override fun initialize() {
        repeat(2) {
            gameBoard.addNewValue()
        }
    }

    override fun canMove(): Boolean = gameBoard.any { it == null }

    override fun hasWon(): Boolean = gameBoard.any { it == 2048 }

    override fun processMove(direction: Direction) {
        if (gameBoard.moveValues(direction)) {
            gameBoard.addNewValue()
        }

    }

    override operator fun get(i: Int, j: Int): Int? = gameBoard.run { get(get(i, j)) }

    private fun GameBoard.addNewValue() {
        val nextValuePair = valueGenerator.nextValue(gameBoard)
        nextValuePair?.let { this[it.first] = it.second }
    }

    private fun GameBoard.moveValues(direction: Direction): Boolean {
        val initialValues = this.getAllCells().map { this[it] }
        when (direction) {
            Direction.UP -> {
                (0..3).forEach {
                    moveValuesInRowOrColumn(this.getColumn(0 until width, it))
                }
            }
            Direction.DOWN -> {
                (0..3).forEach {
                    moveValuesInRowOrColumn(this.getColumn(width - 1 downTo 0, it))
                }
            }
            Direction.LEFT -> {
                (0..3).forEach {
                    moveValuesInRowOrColumn(this.getRow(it, 0 until width))
                }
            }
            Direction.RIGHT -> {
                (0..3).forEach {
                    moveValuesInRowOrColumn(this.getRow(it, width - 1 downTo 0))
                }
            }
        }
        val mergedValues = this.getAllCells().map { this[it] }
        return initialValues != mergedValues
    }

    private fun GameBoard.moveValuesInRowOrColumn(rowOrColumn: List<Cell>): Boolean {
        val initialValues = rowOrColumn.map { this[it] }

        val mergedValues = initialValues.moveAndMergeEqual{ value -> value * 2 }

        rowOrColumn.forEachIndexed { index, value ->
            this[value] =  if (index < mergedValues.size) mergedValues[index] else null
        }

        return initialValues != mergedValues
    }
}