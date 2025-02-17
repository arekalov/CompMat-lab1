package data.model


data class Result(
    val rows: List<Approach>,
) {
    override fun toString(): String {
        if (rows.isEmpty()) return "No data available"

        val size = rows[0].approachVector.size

        val maxColumnWidth = (rows.flatMap { it.approachVector + it.inaccuraciesVector }
            .map { "%.2f".format(it).length } +
                (1..size).map { "x$it".length } +
                (1..size).map { "сигма$it".length })
            .maxOrNull() ?: 0

        val formatPattern = "%${maxColumnWidth}.2f"

        val approachHeaders = (1..size).joinToString(" | ") { "x$it".padStart(maxColumnWidth) }
        val sigmaHeaders = (1..size).joinToString(" | ") { "σ$it".padStart(maxColumnWidth) }

        val totalWidth = (maxColumnWidth + 3) * size * 2 + 11
        val separator = "-".repeat(totalWidth)

        fun formatList(list: List<Double>): String {
            return list.joinToString(" | ") { formatPattern.format(it) }
        }

        val dataLines = rows.mapIndexed { index, approach ->
            "iteration ${index + 1}".padEnd(10) + " || " + formatList(approach.approachVector) + " || " + formatList(
                approach.inaccuraciesVector
            )
        }.joinToString("\n")

        return buildString {
            appendLine("Iteration   || $approachHeaders || $sigmaHeaders")
            appendLine(separator)
            append(dataLines)
        }
    }
}
