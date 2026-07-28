# Production Compose Starter Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Build a compact, production-oriented Compose starter with premium edge-to-edge behavior, reusable motion and haptic primitives, dynamic Material 3 theming, and an explicit agent design contract.

**Architecture:** Keep platform setup in `MainActivity`, reusable rules in small `ui/*` packages, and the replaceable demonstration in `StarterApp`. Use only Android and existing Compose Material 3 dependencies so the clone remains reliable.

**Tech Stack:** Kotlin, Jetpack Compose, Material 3, AndroidX Activity, adaptive launcher icons, JUnit 4, Gradle.

## Global Constraints

- Kotlin and Jetpack Compose only for application UI.
- Both system bars remain transparent while content renders edge-to-edge.
- Never use fade animations or blur effects.
- Never put emoji in app UI, strings, resources, or documentation examples.
- Dynamic color on Android 12+ with polished light and dark fallbacks.
- Semantic haptics for meaningful actions, not continuous scrolling.
- Adaptive and monochrome launcher icon layers must respect the safe zone.
- `testDebugUnitTest` and `assembleDebug` must pass before push.

---

### Task 1: Theme and platform chrome

**Files:**
- Modify: `app/src/main/java/com/hkm/emptyactivity/MainActivity.kt`
- Modify: `app/src/main/java/com/hkm/emptyactivity/ui/theme/Color.kt`
- Modify: `app/src/main/java/com/hkm/emptyactivity/ui/theme/Theme.kt`
- Modify: `app/src/main/java/com/hkm/emptyactivity/ui/theme/Type.kt`
- Create: `app/src/main/java/com/hkm/emptyactivity/ui/system/EdgeToEdge.kt`

**Interfaces:**
- Produces: `StarterTheme(darkTheme, dynamicColor, content)` and `ApplySystemBarAppearance(darkTheme)`.
- Consumes: AndroidX `enableEdgeToEdge` and Material 3 color schemes.

- [ ] Replace the default purple fallback with complete neutral blue-green light and dark schemes.
- [ ] Keep Android 12+ dynamic schemes and derive system icon contrast from `darkTheme`.
- [ ] Configure transparent status/navigation bars and contrast enforcement safely by API level.
- [ ] Reduce `MainActivity` to platform setup plus `StarterApp()`.
- [ ] Run `gradlew.bat testDebugUnitTest assembleDebug`; expect `BUILD SUCCESSFUL`.

### Task 2: Motion and haptic primitives

**Files:**
- Create: `app/src/main/java/com/hkm/emptyactivity/ui/motion/Motion.kt`
- Create: `app/src/main/java/com/hkm/emptyactivity/ui/haptics/Haptics.kt`
- Create: `app/src/test/java/com/hkm/emptyactivity/ui/haptics/HapticIntentTest.kt`

**Interfaces:**
- Produces: `StarterMotion` duration/spring tokens, `HapticIntent`, `rememberHapticPerformer()`, and `HapticPerformer.perform(intent)`.
- Consumes: Compose animation specs and Android `View.performHapticFeedback`.

- [ ] Add a failing unit test that asserts every semantic haptic intent maps to a non-negative platform constant.
- [ ] Run `gradlew.bat testDebugUnitTest`; expect the new test to fail before implementation.
- [ ] Implement selection, confirm, reject, and long-press mappings with API-safe platform feedback.
- [ ] Add shared spring and tween timing tokens with no alpha-based transitions.
- [ ] Run `gradlew.bat testDebugUnitTest`; expect all tests to pass.

### Task 3: Reusable tactile components and showcase

**Files:**
- Create: `app/src/main/java/com/hkm/emptyactivity/ui/components/TactileButton.kt`
- Create: `app/src/main/java/com/hkm/emptyactivity/ui/components/TactileSegmentedControl.kt`
- Create: `app/src/main/java/com/hkm/emptyactivity/ui/StarterApp.kt`

**Interfaces:**
- Produces: `TactileButton`, `TactileSegmentedControl`, and `StarterApp`.
- Consumes: `StarterMotion`, `HapticPerformer`, Material 3 theme tokens, and safe drawing/content insets.

- [ ] Implement press scale and shape/elevation response with ripple and confirm haptic.
- [ ] Implement a two-option segmented control using animated bounds/offset and selection haptic.
- [ ] Build the compact showcase with a spatially expanding guidance card and no alpha animation.
- [ ] Ensure tappable content uses `safeDrawing` and bottom content padding while background renders edge-to-edge.
- [ ] Add content descriptions and minimum touch targets.
- [ ] Run `gradlew.bat testDebugUnitTest assembleDebug`; expect `BUILD SUCCESSFUL`.

### Task 4: Adaptive themed icon and agent contract

**Files:**
- Modify: `app/src/main/res/drawable/ic_launcher_foreground.xml`
- Create: `app/src/main/res/drawable/ic_launcher_monochrome.xml`
- Modify: `app/src/main/res/mipmap-anydpi-v26/ic_launcher.xml`
- Modify: `app/src/main/res/mipmap-anydpi-v26/ic_launcher_round.xml`
- Create: `README.md`

**Interfaces:**
- Produces: an adaptive launcher icon with monochrome layer and the canonical clone/customization contract.
- Consumes: Android adaptive icon resources and all implementation decisions above.

- [ ] Draw a centered neutral starter mark inside the adaptive safe zone.
- [ ] Reference the monochrome drawable from both adaptive icon XML files.
- [ ] Document what agents must keep, replace, validate, and never introduce.
- [ ] Document clone/build commands and the exact showcase replacement path.
- [ ] Scan app source for forbidden alpha/fade/blur implementation and emoji characters.
- [ ] Run `gradlew.bat clean testDebugUnitTest assembleDebug`; expect `BUILD SUCCESSFUL`.
- [ ] Commit all scoped files and push `main` to `origin`.

