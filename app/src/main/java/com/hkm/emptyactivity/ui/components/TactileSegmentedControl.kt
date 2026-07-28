package com.hkm.emptyactivity.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.hkm.emptyactivity.ui.haptics.HapticIntent
import com.hkm.emptyactivity.ui.haptics.rememberHapticPerformer
import com.hkm.emptyactivity.ui.motion.StarterMotion

@Composable
fun TactileSegmentedControl(
    options: List<String>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    require(options.isNotEmpty()) { "At least one option is required" }
    require(selectedIndex in options.indices) { "Selected index must match an option" }

    val haptics = rememberHapticPerformer()
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(4.dp),
    ) {
        val segmentWidth = maxWidth / options.size
        val indicatorOffset by animateDpAsState(
            targetValue = segmentWidth * selectedIndex,
            animationSpec = StarterMotion.spatialSpring(),
            label = "segmentPosition",
        )

        Box(
            modifier = Modifier
                .offset(x = indicatorOffset)
                .width(segmentWidth)
                .defaultMinSize(minHeight = 48.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer),
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            options.forEachIndexed { index, option ->
                val selected = index == selectedIndex
                val textColor by animateColorAsState(
                    targetValue = if (selected) {
                        MaterialTheme.colorScheme.onSecondaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    animationSpec = StarterMotion.spatialSpring(),
                    label = "segmentColor",
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .defaultMinSize(minHeight = 48.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .semantics { this.selected = selected }
                        .clickable(
                            role = Role.RadioButton,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) {
                            if (!selected) {
                                haptics.perform(HapticIntent.Selection)
                                onSelected(index)
                            }
                        }
                        .padding(horizontal = 12.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = option,
                        color = textColor,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
        }
    }
}
