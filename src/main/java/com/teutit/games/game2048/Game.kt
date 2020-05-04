package com.teutit.games.game2048

import com.teutit.games.game2048.board.Direction

interface Game {

    fun initialize()
    fun canMove(): Boolean
    fun hasWon(): Boolean
    fun processMove(direction: Direction)
    operator fun get(i: Int, j: Int): Int?
}