package xyz.xenondevs.nova.util

import java.util.*

private val FORMATTING_FILTER_REGEX = Regex("§.")

fun String.capitalize() = replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

fun String.capitalizeAll(): String {
    if (isEmpty()) return this
    
    val chars = toCharArray()
    chars[0] = chars[0].uppercaseChar()
    for (i in chars.indices) {
        if ((i + 1) >= chars.size) break
        
        val char = chars[i]
        if (char == ' ') {
            chars[i + 1] = chars[i + 1].uppercaseChar()
        }
    }
    
    return String(chars)
}

fun String.insert(offset: Int, charSequence: CharSequence) = StringBuilder(this).insert(offset, charSequence).toString()

fun String.Companion.formatSafely(format: String, vararg args: Any?): String {
    return try {
        String.format(format, *args)
    } catch (e: IllegalFormatException) {
        format
    }
}

fun String.removeMinecraftFormatting(): String {
    return replace(FORMATTING_FILTER_REGEX, "")
}