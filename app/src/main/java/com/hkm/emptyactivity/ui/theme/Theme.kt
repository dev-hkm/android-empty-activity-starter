package com.hkm.emptyactivity.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    primaryContainer = PrimaryContainerLight,
    onPrimaryContainer = OnPrimaryContainerLight,
    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,
    secondaryContainer = SecondaryContainerLight,
    onSecondaryContainer = OnSecondaryContainerLight,
    tertiary = TertiaryLight,
    onTertiary = OnTertiaryLight,
    tertiaryContainer = TertiaryContainerLight,
    onTertiaryContainer = OnTertiaryContainerLight,
    error = ErrorLight,
    onError = OnErrorLight,
    errorContainer = ErrorContainerLight,
    onErrorContainer = OnErrorContainerLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    outline = OutlineLight,
    outlineVariant = OutlineVariantLight,
    surfaceContainerLowest = SurfaceContainerLowestLight,
    surfaceContainerLow = SurfaceContainerLowLight,
    surfaceContainer = SurfaceContainerLight,
    surfaceContainerHigh = SurfaceContainerHighLight,
    surfaceContainerHighest = SurfaceContainerHighestLight,
    inverseSurface = InverseSurfaceLight,
    inverseOnSurface = InverseOnSurfaceLight,
    inversePrimary = InversePrimaryLight
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    primaryContainer = PrimaryContainerDark,
    onPrimaryContainer = OnPrimaryContainerDark,
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    secondaryContainer = SecondaryContainerDark,
    onSecondaryContainer = OnSecondaryContainerDark,
    tertiary = TertiaryDark,
    onTertiary = OnTertiaryDark,
    tertiaryContainer = TertiaryContainerDark,
    onTertiaryContainer = OnTertiaryContainerDark,
    error = ErrorDark,
    onError = OnErrorDark,
    errorContainer = ErrorContainerDark,
    onErrorContainer = OnErrorContainerDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    outline = OutlineDark,
    outlineVariant = OutlineVariantDark,
    surfaceContainerLowest = SurfaceContainerLowestDark,
    surfaceContainerLow = SurfaceContainerLowDark,
    surfaceContainer = SurfaceContainerDark,
    surfaceContainerHigh = SurfaceContainerHighDark,
    surfaceContainerHighest = SurfaceContainerHighestDark,
    inverseSurface = InverseSurfaceDark,
    inverseOnSurface = InverseOnSurfaceDark,
    inversePrimary = InversePrimaryDark
)

@Composable
fun StarterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            val dynamic = if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            dynamic.harmonize(darkTheme)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = StarterTypography,
        shapes = StarterShapes,
        content = content,
    )
}

/**
 * Harmonizes dynamic color schemes extracted from Android 12+ Monet engine.
 * Guards against OEM contrast and inverted luminance bugs.
 */
private fun ColorScheme.harmonize(isDark: Boolean): ColorScheme {
    return if (isDark) {
        val darkBg = if (background.luminance() > 0.25f) Color(0xFF131218) else background
        val darkSurface = if (surface.luminance() > 0.25f) Color(0xFF131218) else surface
        val lightOnSurface = if (onSurface.luminance() < 0.55f) Color(0xFFE6E1E5) else onSurface
        val lightOnBg = if (onBackground.luminance() < 0.55f) Color(0xFFE6E1E5) else onBackground

        copy(
            background = darkBg,
            onBackground = lightOnBg,
            surface = darkSurface,
            onSurface = lightOnSurface,
            surfaceContainerLowest = Color(0xFF0E0D12),
            surfaceContainerLow = Color(0xFF1C1A22),
            surfaceContainer = Color(0xFF201E26),
            surfaceContainerHigh = Color(0xFF2B2831),
            surfaceContainerHighest = Color(0xFF36333C)
        )
    } else {
        val lightBg = if (background.luminance() < 0.70f) Color(0xFFFAF8FD) else background
        val lightSurface = if (surface.luminance() < 0.70f) Color(0xFFFAF8FD) else surface
        val darkOnSurface = if (onSurface.luminance() > 0.40f) Color(0xFF1B1B1F) else onSurface
        val darkOnBg = if (onBackground.luminance() > 0.40f) Color(0xFF1B1B1F) else onBackground

        copy(
            background = lightBg,
            onBackground = darkOnBg,
            surface = lightSurface,
            onSurface = darkOnSurface,
            surfaceContainerLowest = Color(0xFFFFFFFF),
            surfaceContainerLow = Color(0xFFF4F2F7),
            surfaceContainer = Color(0xFFEEEBF2),
            surfaceContainerHigh = Color(0xFFE9E6EC),
            surfaceContainerHighest = Color(0xFFE3E0E6)
        )
    }
}
