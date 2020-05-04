package com.teutit.games.game2048

import com.teutit.games.game2048.board.Cell
import com.teutit.games.game2048.board.Direction
import com.teutit.games.game2048.board.GameBoard
import org.junit.Assert
import org.junit.Test

class TestGame2048 {

    @Test
    fun testMoves() {
        testGame(listOf(
                Move(Pair(0, 0), 2, null, """
                |2 - - -
                |- - - -
                |- - - -
                |- - - -"""),
                Move(Pair(0, 3), 2, null, """
                |2 - - 2
                |- - - -
                |- - - -
                |- - - -"""),
                Move(Pair(2, 1), 4, Direction.RIGHT, """
                |- - - 4
                |- - - -
                |- 4 - -
                |- - - -"""),
                Move(Pair(3, 1), 2, Direction.UP, """
                |- 4 - 4
                |- - - -
                |- - - -
                |- 2 - -"""),
                Move(Pair(1, 1), 2, Direction.LEFT, """
                |8 - - -
                |- 2 - -
                |- - - -
                |2 - - -"""),
                Move(Pair(3, 3), 2, Direction.DOWN, """
                |- - - -
                |- - - -
                |8 - - -
                |2 2 - 2"""),
                Move(Pair(2, 2), 2, Direction.RIGHT, """
                |- - - -
                |- - - -
                |- - 2 8
                |- - 2 4"""),
                Move(Pair(0, 1), 4, Direction.DOWN, """
                |- 4 - -
                |- - - -
                |- - - 8
                |- - 4 4"""),
                Move(Pair(2, 0), 2, Direction.RIGHT, """
                |- - - 4
                |- - - -
                |2 - - 8
                |- - - 8"""),
                Move(Pair(2, 2), 2, Direction.DOWN, """
                |- - - -
                |- - - -
                |- - 2 4
                |2 - - 16"""),
                Move(Pair(1, 2), 2, Direction.DOWN, """
                |- - - -
                |- - 2 -
                |- - - 4
                |2 - 2 16"""),
                Move(Pair(0, 3), 2, Direction.RIGHT, """
                |- - - 2
                |- - - 2
                |- - - 4
                |- - 4 16"""),
                Move(Pair(2, 1), 2, Direction.LEFT, """
                |2 - - -
                |2 - - -
                |4 2 - -
                |4 16 - -"""),
                Move(Pair(0, 2), 2, Direction.DOWN, """
                |- - 2 -
                |- - - -
                |4 2 - -
                |8 16 - -""")
        ))
    }

    private fun testGame(moves: List<Move>) {
        val game = createGame2048(4, TestGame2048ValueGenerator(moves))
        game.initialize()

        run {
            val firstMove = moves[0]
            val secondMove = moves[1]
            Assert.assertEquals("Wrong state after board initialization " +
                    "by ${firstMove.value} at ${firstMove.position} and " +
                    "by ${secondMove.value} at ${secondMove.position}", secondMove.board, game.asString())
        }

        for ((index, move) in moves.withIndex() ) {
            if (move.direction == null) continue
            game.processMove(move.direction)
            Assert.assertEquals("Wrong state after moving ${move.direction} " +
                    "and adding ${move.value} at ${move.position} " +
                    "to \n${moves[index-1].board}\n", move.board, game.asString())
        }
    }

    data class Move(val position: Pair<Int, Int>,
                    val value: Int,
                    val direction: Direction?,
                    val rawBoard: String) {
        val board: String
            get() = rawBoard.trimMargin()
    }

    class TestGame2048ValueGenerator(moves: List<Move>) : Game2048ValueGenerator {
        private val valuesIterator = moves.iterator()
        override fun nextValue(board: GameBoard): Pair<Cell, Int>? {
            val nextValue = valuesIterator.next()
            return board[nextValue.position.first, nextValue.position.second] to nextValue.value
        }

    }

    private fun Game.asString(): String {
        return (0..3).joinToString("\n") { i ->
            (0..3).joinToString(" ") { j ->
                "${this[i, j] ?: "-"}"
            }
        }
    }

}