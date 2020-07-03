package com.teutit.game.games

import com.teutit.game.board.Direction

interface Game {
    fun initialize()
    fun hasWon(): Boolean
    fun canMove(): Boolean
    fun processMove(direction: Direction)
    operator fun get(i: Int, j: Int): Int?
}
