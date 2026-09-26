package com.example.aicoach

import kotlin.math.abs
import kotlin.math.atan2

object PoseMath {
    fun calculateAngle(
        aX: Float, aY: Float,
        bX: Float, bY: Float,
        cX: Float, cY: Float
    ): Double {
        val radians = atan2(cY - bY, cX - bX) - atan2(aY - bY, aX - bX)
        var angle = abs(Math.toDegrees(radians.toDouble()))
        if (angle > 180.0) {
            angle = 360.0 - angle
        }
        return angle
    }
}
