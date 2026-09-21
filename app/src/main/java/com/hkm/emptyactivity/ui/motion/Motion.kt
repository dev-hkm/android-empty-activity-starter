package com.hkm.emptyactivity.ui.motion

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically

object StarterMotion {
    const val QuickDurationMillis = 160
    const val StandardDurationMillis = 280
    const val PressedScale = 0.95f

    fun <T> responsiveSpring(): SpringSpec<T> = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessMedium,
    )

    fun <T> spatialSpring(): SpringSpec<T> = spring(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessMediumLow,
    )

    /**
     * Non-fade, non-blur horizontal slide transitions for screens and tabs.
     */
    fun horizontalSlideEnter(forward: Boolean = true): EnterTransition = slideInHorizontally(
        initialOffsetX = { fullWidth -> if (forward) fullWidth else -fullWidth },
        animationSpec = tween(StandardDurationMillis, easing = FastOutSlowInEasing)
    )

    fun horizontalSlideExit(forward: Boolean = true): ExitTransition = slideOutHorizontally(
        targetOffsetX = { fullWidth -> if (forward) -fullWidth else fullWidth },
        animationSpec = tween(StandardDurationMillis, easing = FastOutSlowInEasing)
    )

    /**
     * Vertical slide transitions for modals, sheets, or floating bars.
     */
    fun verticalSlideEnter(): EnterTransition = slideInVertically(
        initialOffsetY = { it },
        animationSpec = responsiveSpring()
    )

    fun verticalSlideExit(): ExitTransition = slideOutVertically(
        targetOffsetY = { it },
        animationSpec = spatialSpring()
    )
}
