package com.hkm.emptyactivity.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.TouchApp
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material.icons.outlined.Widgets
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hkm.emptyactivity.ui.components.FloatingPillBottomNav
import com.hkm.emptyactivity.ui.components.LucideIconMap
import com.hkm.emptyactivity.ui.components.StarterNavItem
import com.hkm.emptyactivity.ui.components.TactileButton
import com.hkm.emptyactivity.ui.components.TactileSegmentedControl
import com.hkm.emptyactivity.ui.motion.StarterMotion
import com.hkm.emptyactivity.util.HapticUtil

@Composable
fun StarterApp() {
    var currentTab by remember { mutableStateOf("home") }
    val context = LocalContext.current

    val navItems = remember {
        listOf(
            StarterNavItem(
                selectedIcon = LucideIconMap.getIconOrDefault("home"),
                unselectedIcon = LucideIconMap.getIconOrDefault("home"),
                label = "Home",
                route = "home"
            ),
            StarterNavItem(
                selectedIcon = LucideIconMap.getIconOrDefault("widgets"),
                unselectedIcon = LucideIconMap.getIconOrDefault("widgets"),
                label = "Components",
                route = "components"
            ),
            StarterNavItem(
                selectedIcon = LucideIconMap.getIconOrDefault("settings"),
                unselectedIcon = LucideIconMap.getIconOrDefault("settings"),
                label = "Settings",
                route = "settings"
            )
        )
    }

    val selectedIndex = navItems.indexOfFirst { it.route == currentTab }.coerceAtLeast(0)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Multi-tab content with pure non-fade horizontal slide transitions
        AnimatedContent(
            targetState = currentTab,
            transitionSpec = {
                val fromIndex = navItems.indexOfFirst { it.route == initialState }
                val toIndex = navItems.indexOfFirst { it.route == targetState }
                val forward = toIndex >= fromIndex
                StarterMotion.horizontalSlideEnter(forward) togetherWith
                    StarterMotion.horizontalSlideExit(forward)
            },
            label = "tab_slide_content"
        ) { tab ->
            when (tab) {
                "home" -> HomeScreen()
                "components" -> ComponentsScreen()
                "settings" -> SettingsScreen()
            }
        }

        // Floating pill bottom navigation (Pozix style)
        FloatingPillBottomNav(
            selectedIndex = selectedIndex,
            items = navItems,
            onItemSelected = { index ->
                if (navItems[index].route != currentTab) {
                    HapticUtil.navigationChange(context)
                    currentTab = navItems[index].route
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 12.dp)
        )
    }
}

/**
 * Standard scrolling header item sharing the same background layer as the content.
 * Glides naturally off-screen during scroll.
 */
@Composable
private fun ScrollingHeader(
    title: String,
    subtitle: String,
    icon: ImageVector? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (icon != null) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun HomeScreen() {
    val context = LocalContext.current
    var selectedMode by remember { mutableIntStateOf(0) }
    var interactionCount by remember { mutableIntStateOf(0) }
    var guidanceExpanded by remember { mutableStateOf(false) }

    val topPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 16.dp
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 84.dp

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = topPadding, bottom = bottomPadding),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(key = "header") {
            ScrollingHeader(
                title = "Android Starter",
                subtitle = "Seamless edge-to-edge, Nunito typography & tactile motion",
                icon = LucideIconMap.getIconOrDefault("sparkles")
            )
        }

        item(key = "status_card") {
            Card(
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircleOutline,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Production Baseline Ready",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Layout gap resolved. Transparent status & navigation bars enabled. All fonts render in Nunito.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item(key = "segmented_control") {
            Column {
                Text(
                    text = "Tactile Experience Mode",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                TactileSegmentedControl(
                    options = listOf("Calm", "Expressive", "Balanced"),
                    selectedIndex = selectedMode,
                    onSelected = {
                        selectedMode = it
                        HapticUtil.selectionTick(context)
                    }
                )
            }
        }

        item(key = "interactive_action") {
            TactileButton(
                label = "Trigger Subtle Haptic",
                onClick = {
                    interactionCount++
                    HapticUtil.actionConfirm(context)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item(key = "interaction_stat") {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Haptic interactions",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Tactile clicks without intrusive buzzing",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Text(
                        text = interactionCount.toString(),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        item(key = "guidance_card") {
            GuidanceCard(
                expanded = guidanceExpanded,
                onToggle = {
                    HapticUtil.selectionTick(context)
                    guidanceExpanded = !guidanceExpanded
                }
            )
        }
    }
}

@Composable
private fun ComponentsScreen() {
    val context = LocalContext.current
    var switchA by remember { mutableStateOf(true) }
    var switchB by remember { mutableStateOf(false) }

    val topPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 16.dp
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 84.dp

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = topPadding, bottom = bottomPadding),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(key = "header") {
            ScrollingHeader(
                title = "Components",
                subtitle = "Material 3 expressive components & vector icons",
                icon = Icons.Outlined.Widgets
            )
        }

        item(key = "toggles_card") {
            OutlinedCard(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Tactile Switches",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Subtle Ticks", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                            Text("Gentle 5ms tactile response", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Switch(
                            checked = switchA,
                            onCheckedChange = {
                                switchA = it
                                HapticUtil.toggle(context)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colorScheme.primary,
                                checkedTrackColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Strict Non-Fade Motion", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                            Text("Enforces crisp slide transitions", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Switch(
                            checked = switchB,
                            onCheckedChange = {
                                switchB = it
                                HapticUtil.toggle(context)
                            }
                        )
                    }
                }
            }
        }

        item(key = "vector_icons_card") {
            Card(
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Pure Vector Icons (Zero Emojis)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Icons are dynamically resolved from Lucide-style vector mappings matching Material Outlined glyphs.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf("shield", "sparkles", "rocket", "clock", "check-circle").forEach { iconName ->
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                modifier = Modifier.size(44.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = LucideIconMap.getIconOrDefault(iconName),
                                        contentDescription = iconName,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsScreen() {
    val topPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 16.dp
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 84.dp

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = topPadding, bottom = bottomPadding),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(key = "header") {
            ScrollingHeader(
                title = "Design Standards",
                subtitle = "Architectural preferences and styling rules",
                icon = Icons.Outlined.Tune
            )
        }

        item(key = "pref_typography") {
            StandardPreferenceRow(
                icon = LucideIconMap.getIconOrDefault("file-text"),
                title = "Typography",
                description = "Nunito font family (Regular, SemiBold, ExtraBold) set as universal default."
            )
        }

        item(key = "pref_system_bars") {
            StandardPreferenceRow(
                icon = Icons.Outlined.Palette,
                title = "Transparent System Bars",
                description = "Status bar & navigation bar seamlessly bleed into app background without opaque cuts."
            )
        }

        item(key = "pref_header") {
            StandardPreferenceRow(
                icon = Icons.Outlined.Widgets,
                title = "Scrolling Header",
                description = "Header is integrated inside the scroll view and moves off-screen with content."
            )
        }

        item(key = "pref_motion") {
            StandardPreferenceRow(
                icon = Icons.Outlined.Security,
                title = "Slide Motion Only",
                description = "Fade, blur, and opacity transitions are strictly forbidden. Only crisp slides."
            )
        }

        item(key = "pref_haptics") {
            StandardPreferenceRow(
                icon = Icons.Outlined.TouchApp,
                title = "Subtle Haptics",
                description = "Subtle 3-12ms ticks. Absolutely no heavy multi-pulse buzzing."
            )
        }
    }
}

@Composable
private fun StandardPreferenceRow(
    icon: ImageVector,
    title: String,
    description: String
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                modifier = Modifier.size(38.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
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
                onToggle()
            },
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Coding Agent Instructions",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Read the production contract before building",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowDown,
                    contentDescription = "Expand",
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(rotation)
                )
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Replace this showcase with your app screens. Preserve Nunito typography, edge-to-edge transparent system bars, scrolling headers, FloatingPillBottomNav, and subtle HapticUtil ticks.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
