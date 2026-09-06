package com.skinai.app.ml

import android.graphics.Bitmap
import com.skinai.app.data.model.FitzpatrickType
import kotlin.math.atan
import kotlin.math.pow

/**
 * Estimates a Fitzpatrick skin type from a photo using the Individual Typology
 * Angle (ITA°) method — a recognized dermatology formula based on CIE L*a*b* color space.
 *
 * This is an approximate, photo-based estimate (affected by lighting/camera settings),
 * NOT a clinical assessment. This is made clear in the UI.
 */
object SkinToneAnalyzer {

    fun analyze(bitmap: Bitmap): FitzpatrickType {
        val (l, _, b) = averageLab(bitmap)
        val ita = Math.toDegrees(atan((l - 50.0) / b)) // ITA° formula

        return when {
            ita > 55 -> FitzpatrickType.TYPE_I
            ita > 41 -> FitzpatrickType.TYPE_II
            ita > 28 -> FitzpatrickType.TYPE_III
            ita > 10 -> FitzpatrickType.TYPE_IV
            ita > -30 -> FitzpatrickType.TYPE_V
            else -> FitzpatrickType.TYPE_VI
        }
    }

    /** Returns average CIE L*, a*, b* values sampled from the center region of the image (likely skin area). */
    private fun averageLab(bitmap: Bitmap): Triple<Double, Double, Double> {
        val w = bitmap.width
        val h = bitmap.height
        // Sample the central 50% region to avoid background/edges
        val startX = (w * 0.25).toInt()
        val endX = (w * 0.75).toInt()
        val startY = (h * 0.25).toInt()
        val endY = (h * 0.75).toInt()

        var sumL = 0.0
        var sumA = 0.0
        var sumB = 0.0
        var count = 0

        val stepX = maxOf(1, (endX - startX) / 60)
        val stepY = maxOf(1, (endY - startY) / 60)

        var y = startY
        while (y < endY) {
            var x = startX
            while (x < endX) {
                val pixel = bitmap.getPixel(x, y)
                val r = (pixel shr 16 and 0xFF)
                val g = (pixel shr 8 and 0xFF)
                val bl = (pixel and 0xFF)
                val (l, a, bb) = rgbToLab(r, g, bl)
                sumL += l; sumA += a; sumB += bb
                count++
                x += stepX
            }
            y += stepY
        }

        return if (count == 0) Triple(50.0, 0.0, 20.0)
        else Triple(sumL / count, sumA / count, sumB / count)
    }

    private fun rgbToLab(r: Int, g: Int, b: Int): Triple<Double, Double, Double> {
        // sRGB -> linear
        fun toLinear(c: Int): Double {
            val cs = c / 255.0
            return if (cs <= 0.04045) cs / 12.92 else ((cs + 0.055) / 1.055).pow(2.4)
        }
        val rl = toLinear(r); val gl = toLinear(g); val bl = toLinear(b)

        // linear RGB -> XYZ (sRGB, D65)
        val x = rl * 0.4124 + gl * 0.3576 + bl * 0.1805
        val y = rl * 0.2126 + gl * 0.7152 + bl * 0.0722
        val z = rl * 0.0193 + gl * 0.1192 + bl * 0.9505

        // Normalize by D65 white point
        val xn = x / 0.95047
        val yn = y / 1.00000
        val zn = z / 1.08883

        fun f(t: Double): Double =
            if (t > 0.008856) t.pow(1.0 / 3.0) else (7.787 * t) + (16.0 / 116.0)

        val fx = f(xn); val fy = f(yn); val fz = f(zn)

        val L = (116 * fy) - 16
        val A = 500 * (fx - fy)
        val B = 200 * (fy - fz)

        return Triple(L, A, B)
    }
}
