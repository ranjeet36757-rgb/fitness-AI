package com.example.aicoach

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult
import kotlin.math.max

class OverlayView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var results: PoseLandmarkerResult? = null
    private var imageWidth: Int = 1
    private var imageHeight: Int = 1
    private var scaleFactor: Float = 1f

    private val linePaint = Paint().apply {
        color = Color.parseColor("#00FF66")
        strokeWidth = 8f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private val pointPaint = Paint().apply {
        color = Color.parseColor("#FFFF00")
        strokeWidth = 14f
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val connections = listOf(
        Pair(11, 12),
        Pair(11, 13), Pair(13, 15),
        Pair(12, 14), Pair(14, 16),
        Pair(11, 23), Pair(12, 24),
        Pair(23, 24),
        Pair(23, 25), Pair(25, 27),
        Pair(24, 26), Pair(26, 28)
    )

    fun setResults(poseLandmarkerResult: PoseLandmarkerResult, imageWidth: Int, imageHeight: Int) {
        this.results = poseLandmarkerResult
        this.imageWidth = imageWidth
        this.imageHeight = imageHeight
        this.scaleFactor = max(width * 1f / imageWidth, height * 1f / imageHeight)
        invalidate()
    }

    fun clear() {
        results = null
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val currentResult = results ?: return
        if (currentResult.landmarks().isEmpty()) return

        val landmarks = currentResult.landmarks()[0]

        for (connection in connections) {
            val start = landmarks[connection.first]
            val end = landmarks[connection.second]

            val startX = start.x() * width
            val startY = start.y() * height
            val endX = end.x() * width
            val endY = end.y() * height

            canvas.drawLine(startX, startY, endX, endY, linePaint)
        }

        for (landmark in landmarks) {
            val px = landmark.x() * width
            val py = landmark.y() * height
            canvas.drawCircle(px, py, 6f, pointPaint)
        }
    }
}
