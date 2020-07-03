package com.teutit.game.games.game2048

import com.teutit.game.board.Cell
import com.teutit.game.board.GameBoard

interface Game2048ValueGenerator {
    fun generateValue(gameBoard: GameBoard): Pair<Cell, Int>?
}

object Game2048RandomValueGenerator: Game2048ValueGenerator {
    override fun generateValue(gameBoard: GameBoard): Pair<Cell, Int>? {
        val emptyCells = gameBoard.filter { it == null }

        if (emptyCells.isEmpty()) return null

        val position = emptyCells.random()
        val value = listOf(2, 2, 2, 2, 4).random()

        return Pair(position, value)
    }
}