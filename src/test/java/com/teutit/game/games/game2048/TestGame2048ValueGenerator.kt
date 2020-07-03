package com.teutit.game.games.game2048

import org.junit.Assert
import org.junit.Test

class TestGame2048ValueGenerator {

    @Test
    fun testNoValueIsGeneratedWhenBoardIsFull() {
        val game = TestBoard("2422 8224 2224 4222")

        val result = Game2048RandomValueGenerator.generateValue(game.asGameBoard())

        Assert.assertNull("No new elements can be added to a full board", result)
    }

    @Test
    fun test1() = testNewValue("---- ---- -2-- ----")

    @Test
    fun test2() = testNewValue("2222 ---- ---- ----")

    @Test
    fun test3() = testNewValue("2--- 4--- -2-- ---8")

    @Test
    fun test4() = testNewValue("-248 2-2- -2-8 4442")
    

    private fun testNewValue(input: String) {
        val testBoard = TestBoard(input)

        val board = testBoard.asGameBoard()
        val (position, value) = Game2048RandomValueGenerator.generateValue(board)
                ?: throw AssertionError("Value generated can not be null!")

        Assert.assertTrue("Value should be generated in one of the empty positions", position in board.filter { it == null })
        Assert.assertTrue("Value generated should be 2 or 4", value in listOf(2, 4))
    }
}