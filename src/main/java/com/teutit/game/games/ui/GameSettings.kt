package com.teutit.game.games.ui

import java.awt.Color
import kotlin.math.pow

abstract class GameSettings(val backgroundColor: Color, val title: String) {
    abstract fun getBackgroundColor(value: Int): Color
    abstract fun getForegroundColor(value: Int): Color
}

object Game2048Settings: GameSettings(Color(0xbbada0), "Game 2048") {

    override fun getBackgroundColor(value: Int): Color {
        val colors = listOf(Color(0xeee4da), Color(0xede0c8), Color(0xf2b179), Color(0xf59563), Color(0xf67c5f),
                Color(0xf65e3b), Color(0xedcf72), Color(0xedcc61), Color(0xedc850), Color(0xedc53f), Color(0xedc22e))
        val colorsMap = (1..11).map { 2.0.pow(it).toInt() }.zip(colors).toMap()

        return colorsMap[value] ?: Color(0xcdc1b4)
    }

    override fun getForegroundColor(value: Int): Color = if (value < 16) Color(0x776e65) else Color(0xf9f6f2)

}