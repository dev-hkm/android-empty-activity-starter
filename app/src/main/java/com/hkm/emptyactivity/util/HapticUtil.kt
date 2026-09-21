package com.hkm.emptyactivity.util

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

/**
 * Semantic and subtle haptic feedback utility for Android.
 * Provides intentional, gentle vibrations tailored to user actions without being intrusive.
 * Avoids crude, loud, or multi-buzz rattles.
 */
object HapticUtil {

    /** Subtle tick for choosing an option, selecting tabs, picking values */
    fun selectionTick(context: Context) {
        performPredefinedOrOneShot(context, VibrationEffect.EFFECT_TICK, durationMs = 3, amplitude = 20)
    }

    /** Crisp light tick for toggling switches or checkboxes */
    fun toggle(context: Context) {
        performPredefinedOrOneShot(context, VibrationEffect.EFFECT_TICK, durationMs = 5, amplitude = 35)
    }

    /** Action confirmation tap for primary actions (save, apply, submit) */
    fun actionConfirm(context: Context) {
        performPredefinedOrOneShot(context, VibrationEffect.EFFECT_CLICK, durationMs = 12, amplitude = 60)
    }

    /** Gentle success pattern for successful operations */
    fun success(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            performPredefinedOrOneShot(context, VibrationEffect.EFFECT_CLICK, durationMs = 15, amplitude = 70)
        } else {
            performOneShot(context, durationMs = 15, amplitude = 70)
        }
    }

    /** Warning feedback for destructive confirmations or alerts */
    fun warning(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            performWaveform(context, longArrayOf(0, 15, 50, 15), intArrayOf(0, 70, 0, 70))
        } else {
            performOneShot(context, durationMs = 30, amplitude = 70)
        }
    }

    /** Error feedback for validation failures */
    fun error(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            performWaveform(context, longArrayOf(0, 20, 60, 25), intArrayOf(0, 90, 0, 90))
        } else {
            performOneShot(context, durationMs = 40, amplitude = 80)
        }
    }

    /** Tab switch / screen navigation change */
    fun navigationChange(context: Context) {
        performPredefinedOrOneShot(context, VibrationEffect.EFFECT_CLICK, durationMs = 8, amplitude = 40)
    }

    private fun getVibrator(context: Context): Vibrator? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            }
        } catch (_: Exception) {
            null
        }
    }

    private fun performPredefinedOrOneShot(
        context: Context,
        predefinedEffectId: Int,
        durationMs: Long,
        amplitude: Int
    ) {
        try {
            val vibrator = getVibrator(context) ?: return
            if (!vibrator.hasVibrator()) return

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                vibrator.vibrate(VibrationEffect.createPredefined(predefinedEffectId))
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(durationMs, amplitude.coerceIn(1, 255)))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(durationMs)
            }
        } catch (_: Exception) {
            // Fail silently if vibration fails or is unsupported
        }
    }

    private fun performOneShot(context: Context, durationMs: Long, amplitude: Int) {
        try {
            val vibrator = getVibrator(context) ?: return
            if (!vibrator.hasVibrator()) return

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(durationMs, amplitude.coerceIn(1, 255)))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(durationMs)
            }
        } catch (_: Exception) {
            // Fail silently
        }
    }

    private fun performWaveform(context: Context, timings: LongArray, amplitudes: IntArray) {
        try {
            val vibrator = getVibrator(context) ?: return
            if (!vibrator.hasVibrator()) return

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createWaveform(timings, amplitudes, -1))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(timings.sum())
            }
        } catch (_: Exception) {
            // Fail silently
        }
    }
}
