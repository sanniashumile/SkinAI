package com.skinai.app.ml

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import kotlin.math.exp

/**
 * Runs on-device inference for a single skin-scan category model.
 * Handles preprocessing (resize + normalize to [0,1]) and softmax (only if the
 * model doesn't already output normalized probabilities).
 */
class TFLiteClassifier(
    context: Context,
    modelAssetName: String,
    private val inputSize: Int,
    private val outputIsSoftmax: Boolean
) {
    private val interpreter: Interpreter

    init {
        val assetFileDescriptor = context.assets.openFd(modelAssetName)
        val inputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        val modelBuffer: ByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)

        val options = Interpreter.Options().apply {
            setNumThreads(4)
        }
        interpreter = Interpreter(modelBuffer, options)
    }

    /**
     * Runs inference on [bitmap] and returns a probability for each output class index (0..N-1).
     * Caller maps indices to labels using the corresponding ScanCategory.labels list.
     */
    fun classify(bitmap: Bitmap): FloatArray {
        val resized = Bitmap.createScaledBitmap(bitmap, inputSize, inputSize, true)
        val inputBuffer = bitmapToByteBuffer(resized)

        val outputSize = interpreter.getOutputTensor(0).shape().last()
        val output = Array(1) { FloatArray(outputSize) }

        interpreter.run(inputBuffer, output)

        val raw = output[0]
        return if (outputIsSoftmax) raw else softmax(raw)
    }

    private fun bitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val buffer = ByteBuffer.allocateDirect(4 * inputSize * inputSize * 3)
        buffer.order(ByteOrder.nativeOrder())

        val pixels = IntArray(inputSize * inputSize)
        bitmap.getPixels(pixels, 0, inputSize, 0, 0, inputSize, inputSize)

        for (pixel in pixels) {
            val r = (pixel shr 16 and 0xFF) / 255.0f
            val g = (pixel shr 8 and 0xFF) / 255.0f
            val b = (pixel and 0xFF) / 255.0f
            buffer.putFloat(r)
            buffer.putFloat(g)
            buffer.putFloat(b)
        }
        buffer.rewind()
        return buffer
    }

    private fun softmax(logits: FloatArray): FloatArray {
        val max = logits.maxOrNull() ?: 0f
        val exps = logits.map { exp((it - max).toDouble()).toFloat() }
        val sum = exps.sum()
        return exps.map { it / sum }.toFloatArray()
    }

    fun close() {
        interpreter.close()
    }
}
