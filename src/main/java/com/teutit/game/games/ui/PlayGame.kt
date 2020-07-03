package com.teutit.game.games.ui

import com.teutit.game.games.Game
import com.teutit.game.board.Direction
import com.teutit.game.board.createGameBoard
import com.teutit.game.board.createSquareBoard
import com.teutit.game.games.game2048.Game2048RandomValueGenerator
import com.teutit.game.games.game2048.createGame2048
import java.awt.*
import java.awt.RenderingHints.VALUE_ANTIALIAS_ON
import java.awt.RenderingHints.VALUE_STROKE_NORMALIZE
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.*
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.WindowConstants.EXIT_ON_CLOSE
import kotlin.math.pow

private const val FONT_NAME = "Arial"
private const val TILE_SIZE = 64
private const val TILE_MARGIN = 16

fun createPlayGame(game: Game, settings: GameSettings) = PlayGame(game, settings)
class PlayGame(val game: Game, private val settings: GameSettings): JPanel() {
    init {
        isFocusable = true
        addKeyListener(object: KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                if (!game.hasWon() && game.canMove()) {
                    val direction = when(e.keyCode) {
                        VK_LEFT -> Direction.LEFT
                        VK_RIGHT -> Direction.RIGHT
                        VK_UP -> Direction.UP
                        VK_DOWN -> Direction.DOWN
                        else -> null
                    }
                    direction?.let { game.processMove(it) }
                    repaint()
                }
            }
        })
        game.initialize()
    }

    override fun paint(g: Graphics) {
        super.paint(g)
        g.color = settings.backgroundColor
        g.fillRect(0, 0, this.size.width, this.size.height)
        for (i in 0..3) {
            for (j in 0..3) {
                drawTile(g as Graphics2D, game[i, j] ?: 0, j, i)
            }
        }
    }

    private fun drawTile(g: Graphics2D, value:Int, x: Int, y: Int) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, VALUE_STROKE_NORMALIZE)

        g.color = settings.getBackgroundColor(value)
        val xTileOffset = getOffset(x)
        val yTileOffset = getOffset(y)
        g.fillRoundRect(xTileOffset, yTileOffset, TILE_SIZE, TILE_SIZE, 14, 14)

        g.color = settings.getForegroundColor(value)
        val font = getFont(value)
        g.font = font

        val xStringOffset = getXStringOffset(g, value, xTileOffset)
        val yStringOffset = getYStringOffset(g, value, yTileOffset)

        if (value != 0) g.drawString(value.toString(), xStringOffset, yStringOffset)

        if (game.hasWon() || !game.canMove()) {
            g.color = Color(255, 255, 255, 30)
            g.fillRect(0, 0, this.size.width, this.size.height)
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

    private fun getForegroundColor(value: Int): Color = if (value < 16) Color(0x776e65) else Color(0xf9f6f2)

    private fun getOffset(arg: Int) = TILE_MARGIN + arg * (TILE_SIZE + TILE_MARGIN)

    private fun getFont(value: Int): Font {
        val size = if (value < 100) 36 else if (value< 1000) 32 else 24
        return Font(FONT_NAME, Font.BOLD, size)
    }

    private fun getXStringOffset(g: Graphics2D, value: Int, xTileOffset: Int): Int {
        val fontMetrics = g.getFontMetrics(g.font)
        val stringWidth = fontMetrics.stringWidth(value.toString())

        return xTileOffset + (TILE_SIZE - stringWidth)/2
    }

    private fun getYStringOffset(g: Graphics2D, value: Int, yTileOffset: Int): Int {
        val fontMetrics = g.getFontMetrics(g.font)
        val stringHeight = -fontMetrics.getLineMetrics(value.toString(), g).baselineOffsets[2].toInt()

        return yTileOffset + (TILE_SIZE + stringHeight)/2 - 2
    }
}

fun main(){
    val gameSettings = Game2048Settings
    val gameBoard =  createGameBoard(createSquareBoard(4))
    with(JFrame()) {
        title = gameSettings.title
        defaultCloseOperation = EXIT_ON_CLOSE
        size = Dimension(340, 400)
        isResizable = false
        add(createPlayGame(createGame2048(Game2048RandomValueGenerator, gameBoard), gameSettings))
        setLocationRelativeTo(null)
        isVisible =  true
    }
}