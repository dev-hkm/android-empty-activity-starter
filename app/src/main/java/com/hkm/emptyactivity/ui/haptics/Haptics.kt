package com.hkm.emptyactivity.ui.haptics

import android.os.Build
import android.view.HapticFeedbackConstants
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView

enum class HapticIntent(val platformFeedbackConstant: Int) {
    Selection(HapticFeedbackConstants.CLOCK_TICK),
    Confirm(HapticFeedbackConstants.CONFIRM),
    Reject(HapticFeedbackConstants.REJECT),
    LongPress(HapticFeedbackConstants.LONG_PRESS),
}

@Stable
class HapticPerformer internal constructor(private val view: View) {
    fun perform(intent: HapticIntent): Boolean {
        val constant = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> intent.platformFeedbackConstant
            intent == HapticIntent.Confirm -> HapticFeedbackConstants.VIRTUAL_KEY
            intent == HapticIntent.Reject -> HapticFeedbackConstants.LONG_PRESS
            else -> intent.platformFeedbackConstant
        }
        return view.performHapticFeedback(constant)
    }
}

@Composable
fun rememberHapticPerformer(): HapticPerformer {
    val view = LocalView.current
    return remember(view) { HapticPerformer(view) }
}
