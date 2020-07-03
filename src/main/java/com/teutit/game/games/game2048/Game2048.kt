package com.teutit.game.games.game2048

import com.teutit.game.board.Cell
import com.teutit.game.games.Game
import com.teutit.game.board.Direction
import com.teutit.game.board.GameBoard

fun createGame2048(generator: Game2048ValueGenerator, gameBoard: GameBoard): Game = Game2048(generator, gameBoard)
class Game2048(private val generator: Game2048ValueGenerator, private val board: GameBoard): Game {

    override fun initialize() {
        repeat(2) {
            board.addNewValue(generator)
        }
    }

    override fun hasWon(): Boolean = board.any { it == 2048 }

    override fun canMove(): Boolean = board.any { it == null }

    override fun processMove(direction: Direction) {
        if (board.moveValues(direction)) board.addNewValue(generator)
    }

    override fun get(i: Int, j: Int): Int? = board.run {get(get(i, j))}
}

fun GameBoard.addNewValue(generator: Game2048ValueGenerator): Boolean {
    val newValue = generator.generateValue(this)

    if (newValue != null) this[newValue.first] = newValue.second

    return newValue != null
}

fun GameBoard.moveValues(direction: Direction): Boolean {
    val initialValues = this.getAllCells().map { this[it] }
    when(direction) {
        Direction.RIGHT -> {
            (0..3).forEach {
                    moveValuesInRowOrColumn(getRow(it, 3 downTo 0))
            }
        }
        Direction.LEFT -> {
            (0..3).forEach {
                    moveValuesInRowOrColumn(getRow(it, 0..3))
            }
        }
        Direction.UP -> {
            (0..3).forEach {
                    moveValuesInRowOrColumn(getColumn(0..3, it))
            }
        }
        Direction.DOWN -> {
            (0..3).forEach {
                    moveValuesInRowOrColumn(getColumn(3 downTo 0, it))
            }
        }
    }
    val valuesAfterMerge = this.getAllCells().map { this[it] }

    return initialValues != valuesAfterMerge;
}

fun GameBoard.moveValuesInRowOrColumn(rowOrColumn: Collection<Cell>): Boolean {
    val initialValues = rowOrColumn.map { this[it] }

    val mergedValues = initialValues.moveAndMerge { value -> value * 2 }

    for ((index, cell) in rowOrColumn.withIndex()) {
        this[cell] = if (index < mergedValues.size) mergedValues[index] else null
    }
    return initialValues != mergedValues
}