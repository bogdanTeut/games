package com.teutit.game.games.game2048

import com.teutit.game.board.Cell
import com.teutit.game.board.SquareBoard
import org.junit.Assert
import org.junit.Test

class TestMoveValuesInRowOrColumn {

    @Test
    fun testRow() = testMoveValuesInRowOrColumn("-2-4 2--- ---- 4---", { it.getRow(0, 0..3) }, "Row(1, 1..4)",
            "24-- 2--- ---- 4---")

    @Test
    fun testRowReversed() = testMoveValuesInRowOrColumn("-2-4 2--- ---- 4---", { it.getRow(0, 3 downTo 0) }, "Row(1, 4 downTo 1)",
            "--24 2--- ---- 4---")

    @Test
    fun testColumn() = testMoveValuesInRowOrColumn("-2-4 2--- ---- 4---", { it.getColumn(0..3, 0) }, "Column(1..4, 1)",
            "22-4 4--- ---- ----")

    @Test
    fun testColumnReversed() = testMoveValuesInRowOrColumn("-2-4 2--- ---- 4---", { it.getColumn(3 downTo 0, 0) }, "Column(4 downTo 1, 1)",
            "-2-4 ---- 2--- 4---")

    @Test
    fun testNoMove() = testMoveValuesInRowOrColumn("2424 ---- ---- ----", { it.getRow(0, 0..3) }, "Row(1, 1..4)",
             "2424 ---- ---- ----", expectedMoveResult = false)

    private fun testMoveValuesInRowOrColumn(initial: String,
                                            rowOrColumnFunction: (SquareBoard) -> Collection<Cell>,
                                            rowOrColumn: String,
                                            expected: String,
                                            expectedMoveResult: Boolean = true) {

        val initialBoard = TestBoard(initial)
        val board = initialBoard.asGameBoard()

        val moveResult = board.moveValuesInRowOrColumn(rowOrColumnFunction(board))

        val result = board.toTestBoard()

        val expectedBoard = TestBoard(expected)

        Assert.assertEquals("Incorrect result after moving row: $rowOrColumn\n" +
                                     "Input: \n$initialBoard",
                            expectedBoard,
                            result)

        Assert.assertEquals("Incorrect move result after moving ",
                            expectedMoveResult,
                            moveResult)
    }

}