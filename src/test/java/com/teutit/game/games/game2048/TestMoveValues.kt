package com.teutit.game.games.game2048

import com.teutit.game.board.Direction
import org.junit.Assert
import org.junit.Test

class TestMoveValues {

    @Test
    fun testSimpleMove() = testMoveNormalAndMirror("---- ---- -2-- ----", "---- ---- ---2 ----")

    @Test
    fun testNoMove() = testMoveNormalAndMirror("---- ---- ---- 2424", "---- ---- ---- 2424", expectedMoveResult = false)

    @Test
    fun testSeveralMoves() = testMoveNormalAndMirror("2--- -2-- --2- ---2", "---2 ---2 ---2 ---2")

    @Test
    fun testMovesInSomeRows() = testMoveNormalAndMirror("2--- ---- --2- 2424", "---2 ---- ---2 2424")

    @Test
    fun testMoveAndMerge() = testMoveNormalAndMirror("2-2- -2-2 --2- ---2", "---4 ---4 ---2 ---2")

    @Test
    fun testMerge() = testMoveNormalAndMirror("2-22 2-42 22-2 ----", "--24 -242 --24 ----")

    private fun testMoveNormalAndMirror(input: String, expected: String, expectedMoveResult: Boolean = true) {
        val inputBoard = TestBoard(input)
        val expectedBoard = TestBoard(expected)
        testMoveNormalAndReversed(inputBoard, Direction.RIGHT, expectedBoard, expectedMoveResult)
        testMoveNormalAndReversed(inputBoard.mirror(), Direction.DOWN, expectedBoard.mirror(), expectedMoveResult)
    }

    private fun testMoveNormalAndReversed(inputBoard: TestBoard, direction: Direction, expectedBoard: TestBoard, expectedMoveResult: Boolean) {
        testMove(inputBoard, direction, expectedBoard, expectedMoveResult)
        testMove(inputBoard.reversed(), direction.reversed(), expectedBoard.reversed(), expectedMoveResult)
    }

    private fun testMove(inputBoard: TestBoard, direction: Direction, expectedBoard: TestBoard, expectedMoveResult: Boolean) {
        val board = inputBoard.asGameBoard()

        val moveResult = board.moveValues(direction)

        val result = board.toTestBoard()

        Assert.assertEquals(buildString {
                            appendln("Wrong result after moving values")
                            appendln("to $direction")
                            appendln("for input: $inputBoard")
                            },
                expectedBoard,
                result)

        Assert.assertEquals(buildString {
                            appendln("Incorrect value returned for moving")
                            appendln("to ${Direction.RIGHT}")
                            appendln("for input: $inputBoard")
                        },
                expectedMoveResult,
                moveResult)
    }
}