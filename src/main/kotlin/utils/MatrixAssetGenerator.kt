package utils

import java.io.File
import kotlin.random.Random

fun generateMatrixAssetFile(
    filename: String,
    n: Int,
    inaccuracy: Double = 0.01,
    filePath: String = "/Users/arekalov/Itmo/4/CompMat/CompMat-lab1/src/main/resources",
) {
    val file = File("$filePath/$filename")

    file.bufferedWriter().use { writer ->
        writer.write("$inaccuracy")
        writer.newLine()

        repeat(n) {
            val row = (1..(n + 1)).joinToString(" ") {
                String.format("%.2f", Random.nextDouble(-20.0, 20.0))
            }
            writer.write(row)
            writer.newLine()
        }
    }
}

fun main() {
    generateMatrixAssetFile(filename = "asset5", n = 20, inaccuracy = 0.01)
}