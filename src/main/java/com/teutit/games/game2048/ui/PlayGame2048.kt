package com.teutit.games.game2048.ui

import com.teutit.games.game2048.board.Direction
import com.teutit.games.game2048.Game
import com.teutit.games.game2048.createGame2048
import java.awt.*
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.WindowConstants
import kotlin.math.pow

private const val TILE_SIZE = 64
private const val TILE_MARGIN = 16
private const val ARC_WIDTH = 16
private const val FONT_NAME = "Arial"

object Game2048Settings {

    private val emptyColor = Color(0xcdc1b4)
    private val colors = run {
        val colors = listOf(0xeee4da, 0xede0c8, 0xf2b179, 0xf59563, 0xf67c5f, 0xf65e3b,
                0xedcf72, 0xedcc61, 0xedc850, 0xedc53f, 0xedc22e)
        val values = (1..11).map { 2.0.pow(it).toInt() }

        values.zip(colors.map { Color(it) }).toMap()
    }

    fun getBackGroundColor(value: Int): Color = colors[value] ?: emptyColor

    fun getForegroundColor(value: Int): Color = if (value < 16) Color(0x776e65) else Color(0xf9f6f2)

    fun getFont(value: Int): Font {
        val size = if (value < 100) 36 else if (value < 1000) 32 else 24
        return Font(FONT_NAME, Font.BOLD, size)
    }

    val baseBackgroundColor
        get() = Color(0xbbada0)

    val title
        get() = "Game2048"
}


class PlayGame2048(private val settings: Game2048Settings, private val game: Game): JPanel() {

    init {
        isFocusable =  true
        addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                if (game.canMove() && !game.hasWon()) {
                    val direction =  when(e.keyCode) {
                        KeyEvent.VK_LEFT -> Direction.LEFT
                        KeyEvent.VK_RIGHT -> Direction.RIGHT
                        KeyEvent.VK_UP -> Direction.UP
                        KeyEvent.VK_DOWN -> Direction.DOWN
                        else -> null
                    }

                    direction?.let { game.processMove(it) }
                }
                repaint()
            }
        })
        game.initialize()
    }

    override fun paint(g: Graphics) {
        super.paint(g)

        g.color = Game2048Settings.baseBackgroundColor
        g.drawRect(0, 0, 340, 400)

        for (y in 0..3) {
            for (x in 0..3) {
                drawTile(g as Graphics2D, game[y, x] ?: 0, x, y)
            }
        }
    }

    private fun drawTile(g: Graphics2D, value: Int, x: Int, y: Int) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE)
        g.color = Game2048Settings.getBackGroundColor(value)

        val xOffset = calculateOffset(x)
        val yOffset = calculateOffset(y)
        g.fillRoundRect(xOffset, yOffset, TILE_SIZE, TILE_SIZE, ARC_WIDTH, ARC_WIDTH)
        g.color = Game2048Settings.getForegroundColor(value)

        val font = Game2048Settings.getFont(value)
        g.font = font
        val fontMetrics = g.getFontMetrics(font)
        val s = value.toString()
        val stringWidth = fontMetrics.stringWidth(s)
        val stringHeight = -fontMetrics.getLineMetrics(s, g).baselineOffsets[2].toInt()

        if (value != 0) {
            g.drawString(s, xOffset + (TILE_SIZE - stringWidth)/2, yOffset + (TILE_SIZE + stringHeight)/2 - 2)
        }

        if (game.hasWon() || !game.canMove()) {
            g.color = Color(255, 255, 255, 30)
            g.fillRect(0, 0, width, height)
            g.color = Color(78, 139, 202)
            g.font = Font(FONT_NAME, Font.BOLD, 48)

            if (game.hasWon()) {
                g.drawString("You won!", 68, 150)
            }

            if (!game.canMove()) {
                g.drawString("Game over!", 45, 160)
            }
        }
    }

    private fun calculateOffset(arg: Int) = arg * (TILE_MARGIN + TILE_SIZE) + TILE_MARGIN
}

fun main() {
    val settings = Game2048Settings
    val game2048 = createGame2048(4)
    with(JFrame()) {
        title = Game2048Settings.title
        size = Dimension(340, 400)
        //it will terminate on close
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        //middle of the screen
        setLocationRelativeTo(null)
        //non resizable
        isResizable = false
        //add content
        add(PlayGame2048(settings, game2048))
        //show the window
        isVisible = true
    }
}