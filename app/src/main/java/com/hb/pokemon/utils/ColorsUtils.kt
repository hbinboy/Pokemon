package com.hb.pokemon.utils

import android.graphics.Color

/**
 * Utility class for handling color switching based on color IDs.
 *
 * @author Robin
 * @version 1.0
 * @since 2023-10-14
 */
object ColorsUtils {

    /**
     * Switches color based on the given color ID.
     *
     * @param colorId The ID of the color to be switched.
     * @return The corresponding color value.
     */
    fun switchColor(colorId: Int): Int {
        return when (colorId) {
            1 -> Color.BLACK
            2 -> Color.BLUE
            3 -> Color.parseColor("#A52A2A") // Brown
            4 -> Color.GRAY
            5 -> Color.GREEN
            6 -> Color.parseColor("#FFC0CB") // pink
            7 -> Color.parseColor("#800080") // purple
            8 -> Color.RED
            9 -> Color.WHITE
            10 -> Color.YELLOW
            else -> Color.WHITE
        }
    }
}