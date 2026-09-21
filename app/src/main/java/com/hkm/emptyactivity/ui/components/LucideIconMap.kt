package com.hkm.emptyactivity.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.Article
import androidx.compose.material.icons.automirrored.outlined.FormatListBulleted
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
import androidx.compose.material.icons.automirrored.outlined.TrendingDown
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Balance
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Biotech
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.DoneAll
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.FolderOpen
import androidx.compose.material.icons.outlined.FormatQuote
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.LockOpen
import androidx.compose.material.icons.outlined.Memory
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.RocketLaunch
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material.icons.outlined.Storage
import androidx.compose.material.icons.outlined.Terminal
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material.icons.outlined.Widgets
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Minimalist, high-precision Lucide-style vector icon resolver.
 * Maps names to Material Outlined icons with refined 1.5-2dp strokes matching Lucide.
 * Strictly avoids emojis in professional UI applications.
 */
object LucideIconMap {

    fun normalizeIconName(name: String): String {
        var s = name.trim().lowercase().trim(':', ' ', '"', '\'')
        if (s.startsWith("lucide:")) s = s.removePrefix("lucide:")
        if (s.startsWith("lucide-")) s = s.removePrefix("lucide-")
        s = s.replace('_', '-')
        return s
    }

    private val ICONS: Map<String, ImageVector> = mapOf(
        // General & Nav
        "home" to Icons.Outlined.Home,
        "widgets" to Icons.Outlined.Widgets,
        "components" to Icons.Outlined.Widgets,
        "settings" to Icons.Outlined.Settings,
        "gear" to Icons.Outlined.Settings,
        "search" to Icons.Outlined.Search,
        "filter" to Icons.Outlined.FilterList,
        "info" to Icons.Outlined.Info,
        "help" to Icons.AutoMirrored.Outlined.HelpOutline,

        // Status & Alerts
        "check" to Icons.Outlined.Check,
        "check-circle" to Icons.Outlined.CheckCircleOutline,
        "circle-check" to Icons.Outlined.CheckCircleOutline,
        "done" to Icons.Outlined.DoneAll,
        "close" to Icons.Outlined.Close,
        "x" to Icons.Outlined.Close,
        "circle-x" to Icons.Outlined.Cancel,
        "warning" to Icons.Outlined.WarningAmber,
        "triangle-alert" to Icons.Outlined.WarningAmber,
        "error" to Icons.Outlined.ErrorOutline,
        "circle-alert" to Icons.Outlined.ErrorOutline,

        // Actions
        "add" to Icons.Outlined.Add,
        "plus" to Icons.Outlined.Add,
        "plus-circle" to Icons.Outlined.AddCircleOutline,
        "edit" to Icons.Outlined.Edit,
        "delete" to Icons.Outlined.Delete,
        "trash" to Icons.Outlined.Delete,
        "refresh" to Icons.Outlined.Refresh,
        "sync" to Icons.Outlined.Refresh,
        "copy" to Icons.Outlined.ContentCopy,
        "share" to Icons.Outlined.Share,
        "open-in-new" to Icons.AutoMirrored.Outlined.OpenInNew,

        // Navigation Arrows
        "arrow-left" to Icons.AutoMirrored.Outlined.ArrowBack,
        "arrow-right" to Icons.AutoMirrored.Outlined.ArrowForward,
        "trending-up" to Icons.AutoMirrored.Outlined.TrendingUp,
        "trending-down" to Icons.AutoMirrored.Outlined.TrendingDown,

        // Content & Media
        "file-text" to Icons.AutoMirrored.Outlined.Article,
        "book" to Icons.AutoMirrored.Outlined.MenuBook,
        "list" to Icons.AutoMirrored.Outlined.FormatListBulleted,
        "quote" to Icons.Outlined.FormatQuote,
        "layers" to Icons.Outlined.Layers,
        "bookmark" to Icons.Outlined.BookmarkBorder,
        "image" to Icons.Outlined.Image,
        "folder" to Icons.Outlined.Folder,
        "folder-open" to Icons.Outlined.FolderOpen,

        // Highlights & Tech
        "sparkles" to Icons.Outlined.AutoAwesome,
        "star" to Icons.Outlined.StarRate,
        "bolt" to Icons.Outlined.Bolt,
        "zap" to Icons.Outlined.Bolt,
        "flame" to Icons.Outlined.LocalFireDepartment,
        "lightbulb" to Icons.Outlined.Lightbulb,
        "rocket" to Icons.Outlined.RocketLaunch,
        "code" to Icons.Outlined.Code,
        "terminal" to Icons.Outlined.Terminal,
        "chip" to Icons.Outlined.Memory,
        "database" to Icons.Outlined.Storage,
        "clock" to Icons.Outlined.Schedule,
        "calendar" to Icons.Outlined.CalendarToday,

        // Security & User
        "shield" to Icons.Outlined.Shield,
        "lock" to Icons.Outlined.Lock,
        "unlock" to Icons.Outlined.LockOpen,
        "eye" to Icons.Outlined.Visibility,
        "eye-off" to Icons.Outlined.VisibilityOff,
        "user" to Icons.Outlined.Person,
        "thumb-up" to Icons.Outlined.ThumbUp,
        "heart" to Icons.Outlined.FavoriteBorder
    )

    fun getIconOrDefault(name: String, fallback: ImageVector = Icons.Outlined.AutoAwesome): ImageVector {
        val norm = normalizeIconName(name)
        return ICONS[norm] ?: fallback
    }
}
