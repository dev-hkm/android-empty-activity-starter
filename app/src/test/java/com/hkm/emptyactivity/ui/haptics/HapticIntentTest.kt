package com.hkm.emptyactivity.ui.haptics

import org.junit.Assert.assertTrue
import org.junit.Test

class HapticIntentTest {
    @Test
    fun everyIntentMapsToAPlatformFeedbackConstant() {
        HapticIntent.entries.forEach { intent ->
            assertTrue(
                "${intent.name} must map to a platform feedback constant",
                intent.platformFeedbackConstant >= 0,
            )
        }
    }

    @Test
    fun intentsUseDistinctSemanticMappings() {
        val mappings = HapticIntent.entries.map(HapticIntent::platformFeedbackConstant)

        assertTrue("Haptic intents should not all feel identical", mappings.distinct().size > 1)
    }
}
