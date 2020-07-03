package com.teutit.game.games.game2048

import com.teutit.game.board.*
import com.teutit.game.games.Game
import org.junit.Assert
import org.junit.Test

class TestGame2048 {

    @Test
    fun testMoves() {
        testMoves(listOf(
                Move(null, Pair(1, 1), 2, """
            |2 - - -
            |- - - -
            |- - - -
            |- - - -"""),
                Move(null, Pair(1, 4), 2, """
            |2 - - 2
            |- - - -
            |- - - -
            |- - - -"""),
                Move(Direction.RIGHT, Pair(3, 2), 2, """
            |- - - 4
            |- - - -
            |- 2 - -
            |- - - -"""),
                Move(Direction.UP, Pair(4, 3), 4, """
            |- 2 - 4
            |- - - -
            |- - - -
            |- - 4 -"""),
                Move(Direction.DOWN, Pair(4, 3), 4, """
            |- - - -
            |- - - -
            |- - - -
            |- 2 4 4"""),
                Move(Direction.LEFT, Pair(2, 4), 2, """
            |- - - -
            |- - - 2
            |- - - -
            |2 8 - -"""),
                Move(Direction.LEFT, Pair(1, 2), 4, """
            |- 4 - -
            |2 - - -
            |- - - -
            |2 8 - -"""),
                Move(Direction.UP, Pair(2, 3), 2, """
            |4 4 - -
            |- 8 2 -
            |- - - -
            |- - - -"""),
                Move(Direction.RIGHT, Pair(3, 2), 2, """
            |- - - 8
            |- - 8 2
            |- 2 - -
            |- - - -"""),
                Move(Direction.LEFT, Pair(1, 3), 2, """
            |8 - 2 -
            |8 2 - -
            |2 - - -
            |- - - -"""),
                Move(Direction.DOWN, Pair(1, 3), 2, """
            |- - 2 -
            |- - - -
            |16 - - -
            |2 2 2 -"""),
                Move(Direction.RIGHT, Pair(2, 2), 2, """
            |- - - 2
            |- 2 - -
            |- - - 16
            |- - 2 4"""),
                Move(Direction.UP, Pair(4, 2), 2, """
            |- 2 2 2
            |- - - 16
            |- - - 4
            |- 2 - -"""),
                Move(Direction.UP, Pair(4, 1), 4, """
            |- 4 2 2
            |- - - 16
            |- - - 4
            |4 - - -"""),
                Move(Direction.LEFT, Pair(1, 4), 2, """
            |4 4 - 2
            |16 - - -
            |4 - - -
            |4 - - -"""),
                Move(Direction.RIGHT, Pair(2, 1), 2, """
            |- - 8 2
            |2 - - 16
            |- - - 4
            |- - - 4"""),
                Move(Direction.DOWN, Pair(4, 1), 2, """
            |- - - -
            |- - - 2
            |- - - 16
            |2 - 8 8"""),
                Move(Direction.RIGHT, Pair(1, 2), 4, """
            |- 4 - -
            |- - - 2
            |- - - 16
            |- - 2 16"""),
                Move(Direction.UP, Pair(1, 1), 2, """
            |2 4 2 2
            |- - - 32
            |- - - -
            |- - - -""")
        ))
    }

    private fun testMoves(moves: List<Move>) {
        val gameBoard =  createGameBoard(createSquareBoard(4))
        val game = createGame2048(TestGame2048ValueGenerator(moves), gameBoard)
        game.initialize()

        run {
            val firstMove = moves[0]
            val secondMove = moves[1]

            Assert.assertEquals("Wrong state after initialization " +
                    "by placing ${firstMove.value} at ${firstMove.cell} " +
                    "and ${secondMove.value} at ${secondMove.cell}",
                    secondMove.board, game.asString())
        }

        for ((index, move) in moves.withIndex()) {
            if (move.direction == null) continue

            game.processMove(move.direction)

            Assert.assertEquals("Wrong result after moving \n" +
                    "${moves[index-1].board} to ${move.direction} " +
                    "and adding ${move.value} at ${move.cell}",
                    move.board,
                    game.asString())
        }
    }

    class TestGame2048ValueGenerator(moves: List<Move>): Game2048ValueGenerator {
        val iterator = moves.iterator()

        override fun generateValue(gameBoard: GameBoard): Pair<Cell, Int>? {
            val move = iterator.next()
            val location = move.location

            return Pair(Cell(location.first-1, location.second-1), move.value)
        }
    }
}

data class Move(val direction: Direction?,
                val location: Pair<Int, Int>,
                val value: Int,
                val expectedBoard: String) {
    val cell = "Cell(${location.first}, ${location.second})"
    val board = expectedBoard.trimMargin()
}