package com.hkm.emptyactivity.ui.components

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class LucideIconMapTest {

    @Test
    fun normalizeStripsPrefixesAndHyphenates() {
        assertEquals("sparkles", LucideIconMap.normalizeIconName("lucide:sparkles"))
        assertEquals("check-circle", LucideIconMap.normalizeIconName("lucide-check_circle"))
        assertEquals("home", LucideIconMap.normalizeIconName("  HOME  "))
    }

    @Test
    fun resolvesCoreIconsSuccessfully() {
        assertNotNull(LucideIconMap.getIconOrDefault("home"))
        assertNotNull(LucideIconMap.getIconOrDefault("settings"))
        assertNotNull(LucideIconMap.getIconOrDefault("widgets"))
        assertNotNull(LucideIconMap.getIconOrDefault("check-circle"))
    }
}
