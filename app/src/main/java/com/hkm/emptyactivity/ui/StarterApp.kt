package com.hkm.emptyactivity.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.hkm.emptyactivity.ui.components.TactileButton
import com.hkm.emptyactivity.ui.components.TactileSegmentedControl
import com.hkm.emptyactivity.ui.haptics.HapticIntent
import com.hkm.emptyactivity.ui.haptics.rememberHapticPerformer
import com.hkm.emptyactivity.ui.motion.StarterMotion

@Composable
fun StarterApp() {
    var selectedMode by remember { mutableIntStateOf(0) }
    var interactionCount by remember { mutableIntStateOf(0) }
    var guidanceExpanded by remember { mutableStateOf(false) }
    val bottomInset = WindowInsets.safeDrawing.asPaddingValues().calculateBottomPadding()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Surface(
            modifier = Modifier
                .size(220.dp)
                .align(Alignment.TopEnd),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
        ) {}

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Top + WindowInsetsSides.Horizontal,
                    ),
                )
                .imePadding()
                .padding(start = 24.dp, top = 20.dp, end = 24.dp, bottom = bottomInset + 24.dp),
        ) {
            BrandHeader()
            Spacer(modifier = Modifier.height(64.dp))
            Text(
                text = "Built for the edges.",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "A compact Compose reference for dynamic color, tactile motion, safe system UI, and production polish.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(32.dp))
            TactileSegmentedControl(
                options = listOf("Calm", "Expressive"),
                selectedIndex = selectedMode,
                onSelected = { selectedMode = it },
            )
            Spacer(modifier = Modifier.height(16.dp))
            TactileButton(
                label = "Run interaction",
                onClick = { interactionCount += 1 },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))
            InteractionResult(interactionCount = interactionCount)
            Spacer(modifier = Modifier.height(16.dp))
            GuidanceCard(
                expanded = guidanceExpanded,
                onToggle = { guidanceExpanded = !guidanceExpanded },
            )
        }
    }
}

@Composable
private fun BrandHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Surface(
            modifier = Modifier.size(44.dp),
            shape = RoundedCornerShape(15.dp),
            color = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ) {
            StarterMark(modifier = Modifier.padding(10.dp))
        }
        Column {
            Text(
                text = "COMPOSE STARTER",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = "Production baseline",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun StarterMark(modifier: Modifier = Modifier) {
    val color = MaterialTheme.colorScheme.onPrimary
    Canvas(
        modifier = modifier.semantics { contentDescription = "Starter mark" },
    ) {
        val stroke = size.minDimension * 0.13f
        drawCircle(
            color = color,
            style = Stroke(width = stroke),
        )
        drawLine(
            color = color,
            start = Offset(size.width * 0.28f, size.height * 0.62f),
            end = Offset(size.width * 0.72f, size.height * 0.38f),
            strokeWidth = stroke,
            cap = StrokeCap.Round,
        )
    }
}

@Composable
private fun InteractionResult(interactionCount: Int) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.tertiaryContainer,
        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(text = "Interaction state", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Motion and feedback stay synchronized",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            AnimatedContent(
                targetState = interactionCount,
                transitionSpec = {
                    slideInVertically { height -> height } togetherWith
                        slideOutVertically { height -> -height }
                },
                label = "interactionCount",
            ) { count ->
                Text(text = count.toString(), style = MaterialTheme.typography.headlineSmall)
            }
        }
    }
}

@Composable
private fun GuidanceCard(
    expanded: Boolean,
    onToggle: () -> Unit,
) {
    val rotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = StarterMotion.responsiveSpring(),
        label = "guidanceIndicator",
    )
    val haptics = rememberHapticPerformer()
    val interactionSource = remember { MutableInteractionSource() }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(animationSpec = StarterMotion.spatialSpring())
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Button,
            ) {
                haptics.perform(HapticIntent.Selection)
                onToggle()
            },
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Agent guidance", style = MaterialTheme.typography.titleMedium)
                    Text(text = "Open the implementation contract", style = MaterialTheme.typography.bodyMedium)
                }
                Chevron(
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(rotation),
                )
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Replace this showcase, retain the production primitives, and follow README before shipping.",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Composable
private fun Chevron(modifier: Modifier = Modifier) {
    val color = MaterialTheme.colorScheme.onSurfaceVariant
    Canvas(
        modifier = modifier.semantics { contentDescription = "Expand guidance" },
    ) {
        drawLine(
            color = color,
            start = Offset(size.width * 0.28f, size.height * 0.42f),
            end = Offset(size.width * 0.5f, size.height * 0.64f),
            strokeWidth = size.minDimension * 0.1f,
            cap = StrokeCap.Round,
        )
        drawLine(
            color = color,
            start = Offset(size.width * 0.5f, size.height * 0.64f),
            end = Offset(size.width * 0.72f, size.height * 0.42f),
            strokeWidth = size.minDimension * 0.1f,
            cap = StrokeCap.Round,
        )
    }
}
