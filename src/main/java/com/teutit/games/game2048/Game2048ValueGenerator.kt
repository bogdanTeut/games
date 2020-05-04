package com.teutit.games.game2048

import com.teutit.games.game2048.board.Cell
import com.teutit.games.game2048.board.GameBoard

interface Game2048ValueGenerator {
    fun nextValue(board: GameBoard): Pair<Cell, Int>?
}

object RandomGame2048ValueGenerator: Game2048ValueGenerator {

    override fun nextValue(board: GameBoard): Pair<Cell, Int>? {
        val emptyCells = board.filter { it == null }

        return if (emptyCells.isEmpty()) null
               else Pair(emptyCells.random(), listOf(2, 2, 2, 2, 4).random())
    }

}