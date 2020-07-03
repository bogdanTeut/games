package com.teutit.game.games.game2048

import com.teutit.game.games.Game
import com.teutit.game.board.Direction
import com.teutit.game.board.GameBoard
import com.teutit.game.board.createGameBoard
import com.teutit.game.board.createSquareBoard

data class TestBoard(val board: String) {

    private val values = board.split(' ')
            .map { row -> row.map { ch -> if (ch != '-') ch -'0' else null } }

    fun asGameBoard(): GameBoard {
        val board = createGameBoard(createSquareBoard(4))
        board.getAllCells().forEach { cell ->
            board[cell] = values[cell.i][cell.j]
        }

        return board
    }

    fun reversed() = TestBoard(board.reversed())

    fun mirror(): TestBoard {
        val mirrorBoard = (0..3).joinToString(" ") { i ->
            (0..3).joinToString("") { j ->
                "${values[j][i] ?: '-'}"
            }
        }
        return TestBoard(mirrorBoard)
    }

    override fun toString(): String =
        board.split(' ')
                .joinToString("\n") { it.toList().joinToString(" ") }
}

fun Game.asString(): String = asString({i, j -> this.get(i, j)}, "\n", " ")

fun GameBoard.toTestBoard(): TestBoard = TestBoard(asString({i, j -> this[this[i, j]]}, " ", ""))

private fun asString(valueFunction: (Int, Int) -> Int?,
                     rowSeparator: String,
                     elementSeparator: String): String {
    return (0..3).joinToString(rowSeparator) { i ->
                (0..3).joinToString(elementSeparator) { j ->
                    "${valueFunction(i, j) ?: '-'}"
                }
           }
}

fun Direction.reversed() = when (this) {
        Direction.LEFT -> Direction.RIGHT
        Direction.RIGHT -> Direction.LEFT
        Direction.UP -> Direction.DOWN
        Direction.DOWN -> Direction.UP
}