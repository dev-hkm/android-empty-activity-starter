# Android Production Compose Starter

The canonical, verified Jetpack Compose Empty Activity baseline for Android applications and autonomous coding agents.

This starter provides a complete, modern Material 3 foundation with pre-configured edge-to-edge system bars, Nunito typography, tactile spring motion, subtle haptics, and a floating pill bottom navigation bar.

---

## Quick Start & Verification

```bash
# Clone the template
git clone https://github.com/dev-hkm/android-empty-activity-starter.git <project-name>
cd <project-name>

# Build and verify unit tests
./gradlew testDebugUnitTest assembleDebug
```

On Windows:
```powershell
.\gradlew.bat testDebugUnitTest assembleDebug
```

---

## Mandatory Design Contract & Preferences

All coding agents (Hermes, Claude, Antigravity, etc.) implementing apps from this template **MUST strictly adhere to the following rules**:

### 1. Edge-to-Edge System Bars & Seamless Layout
- **Status Bar & Navigation Bar Transparency**: Both the status bar and navigation bar must remain **100% transparent**, seamlessly blending with the app background without hard cuts, borders, or opaque strips.
- **Top Padding Rule**: Compute top padding dynamically to accommodate the transparent status bar:
  ```kotlin
  val topPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 16.dp
  ```
- **Bottom Padding Rule**: Always supply bottom padding so content scrolls cleanly above the floating bottom navigation bar:
  ```kotlin
  val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 84.dp
  ```
- **No Double Padding / Top Gaps**: Never apply double insets (e.g., combining `WindowInsets.safeDrawing.only(Top)` with excessive manual spacers). The top content must start cleanly below the status bar icons.

### 2. Scrolling Header (Single Unified Background)
- **Natural Scroll Integration**: Screen headers (Large Title + Subtitle or Mark) **must be placed inside the scrollable container** (`LazyColumn` or `Column` with `verticalScroll`), sharing the exact same background layer (`MaterialTheme.colorScheme.background`) as the rest of the screen.
- **No Fixed Top Bars by Default**: When the user scrolls down, the header must scroll off-screen naturally with the content. Avoid pinned, boxed TopAppBars unless explicitly requested for sub-screens.

### 3. Floating Pill Bottom Navigation
- **Pill Shape & Placement**: Use `FloatingPillBottomNav` (`ui/components/BottomNavBar.kt`), centered at `Alignment.BottomCenter` with `.navigationBarsPadding().padding(bottom = 12.dp)`.
- **Hugging Content**: The pill is compact (`wrapContentWidth()`, `height(50.dp)`, `RoundedCornerShape(25.dp)`), semi-transparent (`surfaceContainer.copy(alpha = 0.94f)`), with a subtle outline border and soft elevation.
- **Tactile Feedback**:
  - Tactile spring scale bounce on tap (`0.88f` scale with `Spring.DampingRatioMediumBouncy`).
  - Icon micro-bounce on selection (`1.12f` scale).
  - Smooth spring horizontal label expansion.
  - Automatically triggers `HapticUtil.selectionTick(context)`.

### 4. Subtle & Semantic Haptic Feedback
- **Zero Heavy Vibrations**: **Strictly avoid** harsh, jarring, loud, or multi-pulse vibrations (e.g., 2-3 heavy buzzes).
- **Use `HapticUtil` (`util/HapticUtil.kt`)**:
  - `HapticUtil.selectionTick(context)`: Ultra-gentle 3ms / amplitude 20 tick for tab changes, chips, and selections.
  - `HapticUtil.toggle(context)`: Crisp 5ms / amplitude 35 tick for switches and checkboxes.
  - `HapticUtil.actionConfirm(context)`: Subtle 12ms click for button taps and save/submit actions.
  - `HapticUtil.success(context)`: Gentle 15ms click for completed operations.

### 5. Typography: Universal Nunito Font
- **Nunito Font Family**: All 15 Material 3 typography tokens (`displayLarge` down to `labelSmall`) are mapped to `NunitoFontFamily` (`ui/theme/Type.kt`).
- **Font Files**: Embedded in `res/font/` (`nunito_regular.ttf`, `nunito_semibold.ttf`, `nunito_extrabold.ttf`). Never revert to generic system sans-serif.

### 6. Theme: Light / Dark Mode & Contrast Safeguards
- **Monet Dynamic Color (Android 12+)**: Dynamically samples wallpaper hues with built-in luminance safeguards (`Theme.kt`) to eliminate OEM Monet contrast inversion bugs.
- **Fallback Schemes**: Carefully balanced Light and Dark palettes for older Android versions or disabled dynamic color.
- **Default Language**: English is always the default language in `res/values/strings.xml`.

### 7. Icons: Vector Only (Strictly No Emojis)
- **Zero Emojis**: Emojis are **strictly forbidden** in UI text, titles, buttons, badges, and icon substitutes.
- **Vector Icons**: Use Material Outlined icons or `LucideIconMap` (`ui/components/LucideIconMap.kt`) for crisp, professional 1.5-2dp vector strokes matching Lucide design standards.

### 8. Motion: Slide Transitions Only (No Fade, No Blur)
- **Fade Transitions Forbidden**: `fadeIn()` and `fadeOut()` are strictly forbidden across screens and major state changes.
- **Blur Effects Forbidden**: Blur filters and backdrop blurs are forbidden for performance and visual clarity.
- **Slide Transitions**: Use `StarterMotion.horizontalSlideEnter` and `StarterMotion.horizontalSlideExit` (`ui/motion/Motion.kt`) for clean, responsive directional sliding.

---

## Project Structure

```
app/src/main/
├── java/com/hkm/emptyactivity/
│   ├── MainActivity.kt                # Edge-to-edge activity entry point
│   ├── ui/
│   │   ├── StarterApp.kt              # Main multi-tab showcase with scrolling header
│   │   ├── components/
│   │   │   ├── BottomNavBar.kt        # FloatingPillBottomNav (Pozix style)
│   │   │   ├── LucideIconMap.kt       # Vector icon resolver (zero emojis)
│   │   │   ├── TactileButton.kt       # Spring-scale tactile button
│   │   │   └── TactileSegmentedControl.kt # Sliding segmented button
│   │   ├── motion/
│   │   │   └── Motion.kt              # Slide & spring motion tokens (no fade, no blur)
│   │   ├── system/
│   │   │   └── EdgeToEdge.kt          # Transparent system bars controller
│   │   └── theme/
│   │       ├── Color.kt               # Expressive Light/Dark color tokens
│   │       ├── Theme.kt               # Monet harmonization & M3 theme
│   │       └── Type.kt                # Nunito typography definition
│   └── util/
│       └── HapticUtil.kt              # Subtle, non-intrusive haptic feedback
└── res/
    ├── font/                          # Nunito font family (regular, semibold, extrabold)
    └── values/
        └── strings.xml                # English base strings
```

---

## Before Submitting Any New App

1. Replace `StarterApp.kt` with your product screens while retaining the architectural primitives.
2. Verify that the status bar and navigation bar remain transparent throughout all screens.
3. Ensure headers are integrated into scrolling views and move naturally with content.
4. Ensure all icons are vectors and zero emojis exist in the UI.
5. Verify tests and build:
   ```bash
   ./gradlew clean testDebugUnitTest assembleDebug
   ```
