package com.hkm.emptyactivity.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hkm.emptyactivity.ui.haptics.HapticIntent
import com.hkm.emptyactivity.ui.haptics.rememberHapticPerformer
import com.hkm.emptyactivity.ui.motion.StarterMotion

@Composable
fun TactileButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) StarterMotion.PressedScale else 1f,
        animationSpec = StarterMotion.responsiveSpring(),
        label = "buttonScale",
    )
    val cornerRadius by animateDpAsState(
        targetValue = if (pressed) 18.dp else 24.dp,
        animationSpec = StarterMotion.responsiveSpring(),
        label = "buttonShape",
    )
    val elevation by animateDpAsState(
        targetValue = if (pressed) 1.dp else 4.dp,
        animationSpec = StarterMotion.responsiveSpring(),
        label = "buttonElevation",
    )
    val haptics = rememberHapticPerformer()

    Surface(
        onClick = {
            haptics.perform(HapticIntent.Confirm)
            onClick()
        },
        modifier = modifier
            .scale(scale)
            .defaultMinSize(minHeight = 56.dp),
        enabled = enabled,
        shape = RoundedCornerShape(cornerRadius),
        color = if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
        contentColor = if (enabled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
        tonalElevation = elevation,
        shadowElevation = elevation,
        interactionSource = interactionSource,
    ) {
        Box(
            modifier = Modifier.padding(contentPadding),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = Color.Unspecified,
            )
        }
    }
}
