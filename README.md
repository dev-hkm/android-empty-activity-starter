# Android Production Compose Starter

A small Jetpack Compose baseline for coding agents that need to start from a reliable Android project instead of recreating Gradle and platform setup from scratch.

The included screen is a living reference, not a product template. It demonstrates the expected production behavior while keeping the project easy to replace with a real app.

## Clone and verify

```bash
git clone https://github.com/dev-hkm/android-empty-activity-starter.git
cd android-empty-activity-starter
./gradlew testDebugUnitTest assembleDebug
```

On Windows PowerShell, use `./gradlew.bat testDebugUnitTest assembleDebug`. If Java is not detected, point `JAVA_HOME` to the Android Studio JBR before running Gradle.

## Agent instructions

When building a real app from this repository:

1. Implement the product end to end. Do not ask the user to edit source files manually.
2. Replace `app/src/main/java/com/hkm/emptyactivity/ui/StarterApp.kt` with the real screens and navigation.
3. Keep and reuse the focused primitives under `ui/theme`, `ui/system`, `ui/motion`, `ui/haptics`, and `ui/components` when they fit the product.
4. Rename the package, application ID, app label, theme, and starter-specific symbols for the real product.
5. Replace every launcher icon layer with an original, app-specific mark.
6. Run unit tests and `assembleDebug` after changes. Never report completion from source inspection alone.

## Non-negotiable production contract

### System UI and layout

- Render app surfaces edge to edge behind the status and navigation bars.
- Keep system bars transparent and select light or dark system icons for the current theme.
- Apply `WindowInsets.safeDrawing` at interactive content boundaries. Do not paint opaque strips across the top or bottom merely to avoid the system bars.
- Account for gesture navigation, three-button navigation, display cutouts, landscape, and the software keyboard.
- Backgrounds may extend behind system UI; tappable controls and essential text may not be clipped or obstructed.

### Material 3 and color

- Use Material 3 Compose components and theme tokens.
- Support Android 12+ dynamic color in both light and dark mode.
- Maintain intentional light and dark fallback schemes for older Android releases.
- Do not hard-code product colors inside reusable components. Read them from `MaterialTheme.colorScheme`.
- Keep typography, corner radius, spacing, elevation, and touch targets internally consistent.

### Motion

- Fade animations are forbidden, including default transitions that quietly introduce a fade.
- Blur effects are forbidden.
- Prefer spring, slide, scale, expand, shrink, bounds, shape, color, and elevation transitions.
- Make navigation spatially understandable and give every state change an intentional response.
- Keep frequent micro-interactions quick. Do not animate continuously without a functional reason.
- Use Compose animation APIs so the platform animator-duration accessibility setting remains respected.

### Haptics

- Use semantic intents from `ui/haptics/Haptics.kt`: selection, confirm, reject, and long press.
- Trigger haptics when an action commits or meaningfully changes state.
- Do not vibrate during normal scrolling or repeatedly on every frame of a drag.
- Prefer platform haptic feedback. Do not request vibration permission for ordinary UI feedback.

### Icons and visual language

- Emoji are forbidden in UI text, resources, sample data, and icon substitutes.
- Use original vector artwork or a consistent professional icon library.
- Every derived app needs an adaptive launcher icon with foreground, background, and monochrome layers.
- The monochrome layer enables Android 13+ themed icons to follow the user's system palette.
- Keep the meaningful foreground artwork inside the adaptive icon safe zone. It must not look tiny, touch the mask edge, or become unrecognizable under circular and rounded-square masks.
- The starter mark is deliberately neutral. Do not ship it as the identity of a derived app.

### Interaction quality and accessibility

- Every tappable element needs visible pressed feedback and a minimum 48 dp touch target.
- Use subtle haptics and motion together; neither should overwhelm the action.
- Add meaningful content descriptions for non-text controls and preserve correct semantic roles and selected states.
- Check layouts with larger font sizes and long text. Avoid fixed heights around text content.
- Preserve sufficient contrast in dynamic, fallback light, and fallback dark color schemes.

## What the starter demonstrates

- `MainActivity.kt`: minimal activity and edge-to-edge entry point.
- `ui/system/EdgeToEdge.kt`: transparent system bars with correct icon contrast.
- `ui/theme`: dynamic color plus complete light and dark fallbacks.
- `ui/motion/Motion.kt`: shared non-fade spatial motion tokens.
- `ui/haptics/Haptics.kt`: API-safe semantic feedback.
- `ui/components`: tactile reusable controls with press and selection responses.
- `ui/StarterApp.kt`: replaceable single-screen showcase using safe insets.
- Launcher resources: adaptive foreground/background plus a dedicated monochrome layer.

## Before delivery

- Test light, dark, and dynamic color modes.
- Test gesture and three-button navigation.
- Test a device with a display cutout and a small screen.
- Test large font scale and the software keyboard.
- Verify every state transition without fade or blur.
- Verify haptics feel intentional and are not repeated excessively.
- Inspect adaptive icon previews under common launcher masks and themed icon mode.
- Scan tracked files for credentials and machine-specific paths.
- Run `./gradlew clean testDebugUnitTest assembleDebug` and require `BUILD SUCCESSFUL`.

Design rationale and the implementation plan are stored under `docs/superpowers` for agents that need deeper context.
