package io.kvision.test


object Formatting {

    /**
     * Format a HTML string in a standardized manner, with one HTML element per line.
     * This helps with highlighting HTML differences in test assertions.
     */
    fun normalizeHtml(raw: String): String {
        return raw
            .replace("<", "\n<")
            .replace(">", ">\n")
            .split("\n")
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .joinToString("\n")
    }

    /** @see [normalizeHtml] */
    fun normalizeHtml(raw: String?): String? {
        return if (raw == null) null else normalizeHtml(raw)
    }

}
