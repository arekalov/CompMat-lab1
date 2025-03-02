package data.model

import kotlin.math.max

data class Result(
    val rows: List<Approach>,
) {
    override fun toString(): String {
        if (rows.isEmpty()) return "No data available"

        val size = rows[0].approachVector.size
        val iterationsColumnWidth = "Iteration".length.coerceAtLeast("iteration XX".length)

        val columnWidths = MutableList(size * 2) { 0 }

        for (i in 0..<size) {
            val xHeaderWidth = "x${i + 1}".length
            val sigmaHeaderWidth = "σ${i + 1}".length
            val maxXWidth = rows.maxOf { "% .4f".format(it.approachVector[i]).length }
            val maxSigmaWidth = rows.maxOf { "% .4f".format(it.inaccuraciesVector[i]).length }

            columnWidths[i] = max(xHeaderWidth, maxXWidth)
            columnWidths[size + i] = max(sigmaHeaderWidth, maxSigmaWidth)
        }

        val approachHeaders = (0..<size)
            .joinToString(" | ") { "x${it + 1}".padStart(columnWidths[it]) }

        val sigmaHeaders = (0..<size)
            .joinToString(" | ") { "σ${it + 1}".padStart(columnWidths[size + it]) }

        val separatorLength = iterationsColumnWidth + 4 +
                columnWidths.sum() + (3 * (columnWidths.size - 1)) + 4
        val separator = "-".repeat(separatorLength)

        fun formatList(list: List<Double>, widths: List<Int>): String {
            return list.mapIndexed { i, value ->
                "% .4f".format(value).padStart(widths[i])
            }.joinToString(" | ")
        }

        val dataLines = rows.mapIndexed { index, approach ->
            val iterationLabel = "iteration ${index + 1}".padEnd(iterationsColumnWidth)
            val xVals = formatList(approach.approachVector, columnWidths.subList(0, size))
            val sigmaVals = formatList(approach.inaccuraciesVector, columnWidths.subList(size, size * 2))
            "$iterationLabel || $xVals || $sigmaVals"
        }.joinToString("\n")

        return buildString {
            appendLine("${"Iteration".padEnd(iterationsColumnWidth)} || $approachHeaders || $sigmaHeaders")
            appendLine(separator)
            append(dataLines)
        }
    }
}
