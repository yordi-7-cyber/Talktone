package com.example.talktone.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import kotlin.math.cos
import kotlin.math.sin

// ── Color palettes ────────────────────────────────────────────────────────────

object AppColors {
    // Dark backgrounds
    val darkBg1 = listOf(Color(0xFF0D0221), Color(0xFF1A0A2E), Color(0xFF0D1B2A))
    val darkBg2 = listOf(Color(0xFF0A1628), Color(0xFF1A0A2E), Color(0xFF2D1B69))
    val darkBg3 = listOf(Color(0xFF1A0A2E), Color(0xFF0D1B2A), Color(0xFF16213E))

    // Light backgrounds
    val lightBg1 = listOf(Color(0xFFFFF8F0), Color(0xFFF5E6D3), Color(0xFFEDD9C0))
    val lightBg2 = listOf(Color(0xFFF0F4FF), Color(0xFFE8EEFF), Color(0xFFD8E4FF))
    val lightBg3 = listOf(Color(0xFFFFF0F5), Color(0xFFFFE0EC), Color(0xFFFFD0E0))

    // Accent
    val gold = Color(0xFFD4AF37)
    val goldLight = Color(0xFFFFD700)
    val green = Color(0xFF078930)
    val red = Color(0xFFDA121A)
}

// ── Main background composable ────────────────────────────────────────────────

@Composable
fun EthiopianBackground(
    isDark: Boolean,
    style: BgStyle = BgStyle.CROSS,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val bgColors = if (isDark) AppColors.darkBg1 else AppColors.lightBg1
    val patternColor = if (isDark) Color.White.copy(alpha = 0.04f) else Color(0xFF4A0E8F).copy(alpha = 0.06f)
    val accentColor = AppColors.gold.copy(alpha = if (isDark) 0.07f else 0.1f)

    Box(modifier = modifier.fillMaxSize()) {
        // Base gradient
        Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(bgColors)))

        // Pattern overlay
        Canvas(modifier = Modifier.fillMaxSize()) {
            when (style) {
                BgStyle.CROSS -> drawEthiopianCrossPattern(patternColor, accentColor)
                BgStyle.GEOMETRIC -> drawGeometricPattern(patternColor, accentColor)
                BgStyle.DOTS -> drawDotPattern(patternColor)
                BgStyle.LINES -> drawDiagonalLines(patternColor)
            }
        }

        // Glow orbs
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Top-left gold orb
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(AppColors.gold.copy(alpha = if (isDark) 0.15f else 0.1f), Color.Transparent),
                    center = Offset(0f, 0f),
                    radius = size.width * 0.6f
                ),
                radius = size.width * 0.6f,
                center = Offset(-size.width * 0.1f, -size.height * 0.05f)
            )
            // Bottom-right green orb
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(AppColors.green.copy(alpha = if (isDark) 0.1f else 0.07f), Color.Transparent),
                    center = Offset(size.width, size.height),
                    radius = size.width * 0.5f
                ),
                radius = size.width * 0.5f,
                center = Offset(size.width * 1.1f, size.height * 1.05f)
            )
        }

        content()
    }
}

enum class BgStyle { CROSS, GEOMETRIC, DOTS, LINES }

// ── Pattern drawing functions ─────────────────────────────────────────────────

private fun DrawScope.drawEthiopianCrossPattern(color: Color, accentColor: Color) {
    val spacing = 120f
    val crossSize = 18f
    val cols = (size.width / spacing).toInt() + 2
    val rows = (size.height / spacing).toInt() + 2

    for (row in 0..rows) {
        for (col in 0..cols) {
            val x = col * spacing - spacing / 2
            val y = row * spacing - spacing / 2
            val isAccent = (row + col) % 5 == 0
            drawEthiopianCross(Offset(x, y), crossSize, if (isAccent) accentColor else color)
        }
    }
}

private fun DrawScope.drawEthiopianCross(center: Offset, size: Float, color: Color) {
    val stroke = Stroke(width = 1.5f, cap = StrokeCap.Round)
    val arm = size * 0.5f
    val tip = size * 0.2f

    // Vertical arm
    drawLine(color, Offset(center.x, center.y - arm), Offset(center.x, center.y + arm), strokeWidth = 1.5f)
    // Horizontal arm
    drawLine(color, Offset(center.x - arm, center.y), Offset(center.x + arm, center.y), strokeWidth = 1.5f)
    // Diagonal decorations (Ethiopian cross style)
    drawLine(color, Offset(center.x - tip, center.y - arm + tip), Offset(center.x + tip, center.y - arm + tip), strokeWidth = 1f)
    drawLine(color, Offset(center.x - tip, center.y + arm - tip), Offset(center.x + tip, center.y + arm - tip), strokeWidth = 1f)
    drawLine(color, Offset(center.x - arm + tip, center.y - tip), Offset(center.x - arm + tip, center.y + tip), strokeWidth = 1f)
    drawLine(color, Offset(center.x + arm - tip, center.y - tip), Offset(center.x + arm - tip, center.y + tip), strokeWidth = 1f)
}

private fun DrawScope.drawGeometricPattern(color: Color, accentColor: Color) {
    val spacing = 80f
    val cols = (size.width / spacing).toInt() + 2
    val rows = (size.height / spacing).toInt() + 2

    for (row in 0..rows) {
        for (col in 0..cols) {
            val x = col * spacing + if (row % 2 == 0) 0f else spacing / 2
            val y = row * spacing * 0.866f
            val isAccent = (row * cols + col) % 7 == 0
            val c = if (isAccent) accentColor else color
            // Hexagon outline
            drawHexagon(Offset(x, y), 22f, c)
        }
    }
}

private fun DrawScope.drawHexagon(center: Offset, radius: Float, color: Color) {
    val path = Path()
    for (i in 0..5) {
        val angle = Math.PI / 3 * i - Math.PI / 6
        val px = center.x + radius * cos(angle).toFloat()
        val py = center.y + radius * sin(angle).toFloat()
        if (i == 0) path.moveTo(px, py) else path.lineTo(px, py)
    }
    path.close()
    drawPath(path, color, style = Stroke(width = 1f))
}

private fun DrawScope.drawDotPattern(color: Color) {
    val spacing = 30f
    val cols = (size.width / spacing).toInt() + 2
    val rows = (size.height / spacing).toInt() + 2
    for (row in 0..rows) {
        for (col in 0..cols) {
            val x = col * spacing
            val y = row * spacing
            val r = if ((row + col) % 4 == 0) 2.5f else 1.2f
            drawCircle(color, radius = r, center = Offset(x, y))
        }
    }
}

private fun DrawScope.drawDiagonalLines(color: Color) {
    val spacing = 40f
    val count = ((size.width + size.height) / spacing).toInt() + 2
    for (i in 0..count) {
        val start = i * spacing
        drawLine(color,
            start = Offset(start - size.height, 0f),
            end = Offset(start, size.height),
            strokeWidth = 1f
        )
    }
}
