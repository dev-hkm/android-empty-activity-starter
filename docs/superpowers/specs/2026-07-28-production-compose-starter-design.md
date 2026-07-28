# Production Compose Starter Design

## Goal

Turn the default Android Studio Empty Activity into a small, buildable reference for premium production UI. The starter must teach coding agents the expected Android behavior without forcing future apps to remove a large demo application.

## Architecture

`MainActivity` owns only platform window setup and mounts `StarterApp`. Reusable production rules live in focused packages under `ui/system`, `ui/motion`, `ui/haptics`, `ui/components`, and `ui/theme`. `StarterApp` is a single-screen living reference that exercises those primitives and can be replaced without changing them.

## System UI and layout

- Enable edge-to-edge so app surfaces render behind the status and navigation bars.
- Keep both system bars transparent and set light/dark system icon appearance from the active color scheme.
- Apply insets at the content and interaction boundaries, not by painting opaque bars across the screen.
- Support gesture navigation, three-button navigation, display cutouts, portrait, landscape, and keyboard insets without clipping interactive content.

## Material and color

- Use Jetpack Compose and Material 3 only.
- Use Android 12+ dynamic light and dark color schemes.
- Provide complete, intentional light and dark fallback schemes for older Android versions.
- Use Material typography and shape tokens without downloading a runtime font.
- Avoid hard-coded colors inside reusable UI components.

## Motion

- No fade animation and no blur effect anywhere in app code or guidance.
- Motion uses spring, slide, scale, expand, shrink, bounds, and shape/elevation changes.
- Respect the platform animator-duration accessibility setting by relying on Compose animation APIs.
- Keep repeated micro-interactions short and navigation/state transitions spatially understandable.

## Haptics

- Centralize semantic haptic intents: selection, confirm, reject, and long press.
- Trigger feedback only for committed or meaningful actions, never continuously during ordinary scrolling.
- Use platform haptic APIs with safe fallbacks and no vibration permission.

## Showcase

The starter screen contains a compact header, a tactile primary action, a segmented control, and an expandable guidance card. It demonstrates edge-to-edge layout, dynamic Material 3 colors, non-fade motion, ripple/press feedback, and semantic haptics. All visible copy is English and contains no emoji.

## Launcher icon

- Keep a correctly sized adaptive foreground and background.
- Add a monochrome launcher drawable and manifest reference so Android 13+ themed icons follow system dynamic color.
- The neutral starter mark should be simple enough for coding agents to replace with an app-specific concept.
- README must require every derived app to replace all launcher layers with an app-specific icon and verify safe-zone sizing.

## Agent contract and documentation

README is the canonical instruction sheet for agents cloning the repository. It distinguishes reusable starter infrastructure from replaceable showcase code and requires edge-to-edge, Material 3, dynamic color, polished light/dark modes, non-fade/non-blur motion, no emoji, semantic haptics, adaptive themed icons, accessibility, tests, and a green build before delivery.

## Validation

- Unit-test stable design tokens and haptic intent mapping where practical.
- Run `testDebugUnitTest` and `assembleDebug`.
- Inspect the merged manifest for the monochrome icon reference.
- Scan source and README for forbidden fade, blur, and emoji usage, allowing those words only where documenting the prohibition.

