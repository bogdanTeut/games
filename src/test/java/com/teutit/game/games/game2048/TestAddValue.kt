package com.teutit.game.games.game2048

import org.junit.Assert
import org.junit.Test

class TestAddValue {

    @Test
    fun test1() = testAddValue("---- ---- -2-- ----")

    @Test
    fun test2() = testAddValue("2222 ---- ---- ----")

    @Test
    fun test3() = testAddValue("2--- 4--- -2-- ---8")

    @Test
    fun test4() = testAddValue("-248 2-2- -2-8 4442")

    private fun testAddValue(input: String) {
        val testBoard = TestBoard(input)

        val game = testBoard.asGameBoard()

        game.addNewValue(Game2048RandomValueGenerator)

        val result = game.toTestBoard()

        Assert.assertEquals(buildString {
                                appendln("Only one value should be different after adding one new element.")
                                appendln("Input")
                                appendln(testBoard)
                                appendln("Result")
                                appendln(result)
                            },
                1, testBoard.board.indices.count { testBoard.board[it] != result.board[it] } )
    }
}