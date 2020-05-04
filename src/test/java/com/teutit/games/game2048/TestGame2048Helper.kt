package com.teutit.games.game2048

import org.junit.Assert
import org.junit.Test

class TestGame2048Helper {

    @Test
    fun testMoveAndMergeValues() {
        testMerge(listOf("a", null, null, null), listOf("a"))
        testMerge(listOf("b", null, null, "a"), listOf("b", "a"))
        testMerge(listOf(null, "b", null, "a"), listOf("b", "a"))
        testMerge(listOf("a", "a", null, null), listOf("aa"))
        testMerge(listOf(null, "a", "a", null), listOf("aa"))
        testMerge(listOf(null, null, "a", "a"), listOf("aa"))
        testMerge(listOf("a", null, "a", null), listOf("aa"))
        testMerge(listOf("a", null, null, "a"), listOf("aa"))
        testMerge(listOf(null, "a", null, "a"), listOf("aa"))
        testMerge(listOf("a", null, "a", "a"), listOf("aa", "a"))
        testMerge(listOf("a", null, "a", "b"), listOf("aa", "b"))
        testMerge(listOf("a", "a", null, "b"), listOf("aa", "b"))
        testMerge(listOf("a", "b", "a", null), listOf("a", "b", "a"))
        testMerge(listOf("a", null, "b", "a"), listOf("a", "b", "a"))
        testMerge(listOf("a", "a", "b", "a"), listOf("aa", "b", "a"))
        testMerge(listOf("a", "a", "b", "b"), listOf("aa", "bb"))
        testMerge(listOf("a", "a", "a", null), listOf("aa", "a"))
        testMerge(listOf("a", null, "a", "a"), listOf("aa", "a"))
        testMerge(listOf("a", "a", "a", "a"), listOf("aa", "a", "a"))
    }

    private fun testMerge(input: List<String?>, expected: List<String>) {
        val result = input.moveAndMergeEqual { it.repeat(2) }

        Assert.assertEquals("Wrong result for $input.moveAndMergeEqual()", expected, result)
    }
}