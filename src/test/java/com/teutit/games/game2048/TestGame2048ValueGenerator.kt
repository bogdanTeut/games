package com.teutit.games.game2048

import com.teutit.games.game2048.board.GameBoard
import com.teutit.games.game2048.board.createGameBoard
import org.junit.Assert
import org.junit.Test
import java.lang.AssertionError

class TestGame2048ValueGenerator {

    @Test
    fun testNoNewElementWhenFullBoard() {
        val board = createBoard("4248 2824 8248 2482")
        val nextValue = RandomGame2048ValueGenerator.nextValue(board)

        Assert.assertNull("New elements can not be added when the board is full", nextValue)
    }

    @Test
    fun testNewValueGenerated() {
        testNextNonNullValue("---- ---- -2-- ----")
        testNextNonNullValue("2222 ---- ---- ----")
        testNextNonNullValue("2--- 4--- -2-- ---8")
        testNextNonNullValue("0248 2-2- -2-8 4442")
    }

    private fun testNextNonNullValue(input: String) {
        val board = createBoard(input)
        val (cell, value) = RandomGame2048ValueGenerator.nextValue(board)
                ?: throw AssertionError("New value generated should not be null for $board")

        val emptyValues = board.filter { it == null }
        Assert.assertTrue("Value generated should be added to one of the empty cells", cell in emptyValues)
        Assert.assertTrue("Value generated should be either 2 or 4.", value in setOf(2, 4))
    }

    private fun createBoard(input: String): GameBoard {
        val board = createGameBoard(4)

        val testBoard = TestBoard(input)
        board.getAllCells()
                .forEach{ cell -> board[cell] = testBoard.values[cell.i][cell.j] }
        return board
    }

    class TestBoard(private val input: String) {
        val values: List<List<Int?>> by lazy {
            input.trim()
                    .split(' ')
                    .map{ row -> row.map { ch -> if (ch == '-') null else ch - '0' }}
        }
    }
}