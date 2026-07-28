package com.hkm.emptyactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import com.hkm.emptyactivity.ui.StarterApp
import com.hkm.emptyactivity.ui.system.ApplySystemBarAppearance
import com.hkm.emptyactivity.ui.theme.StarterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val darkTheme = isSystemInDarkTheme()
            StarterTheme(darkTheme = darkTheme) {
                ApplySystemBarAppearance(darkTheme = darkTheme)
                StarterApp()
            }
        }
    }
}
