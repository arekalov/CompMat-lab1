package ui.io

import data.model.Matrix
import utils.StringConstants
import java.io.File
import java.nio.charset.Charset

const val ACCEPTABLE_INACCURACY = 0.01

class MatrixReader {
    private fun readFromFile(fileName: String, charset: Charset = Charsets.UTF_8): Pair<List<String>, Double> = try {
        val fileLines = File(fileName).readLines(charset = charset)
        fileLines.subList(1, fileLines.size) to fileLines.first().toDouble()
    } catch (ex: Exception) {
        throw MatrixReaderExceptions.IncorrectFileName()
    }

    private fun readFromConsole(): Pair<List<String>, Double> = try {
        IoController.printMessage(StringConstants.INPUT_ROW_COUNT_MSG)
        val rowsCount = readln().split(" ")
        val inaccuracy = rowsCount.getOrNull(1)?.toDouble() ?: ACCEPTABLE_INACCURACY
        IoController.printMessage(StringConstants.INPUT_ROWS_MS)
        val data = mutableListOf<String>()
        repeat(rowsCount[0].toInt()) {
            data.add(readln())
        }
        data to inaccuracy
    } catch (ex: Exception) {
        throw MatrixReaderExceptions.IncorrectMatrixSize()
    }

    private fun getParsedData(data: List<String>): List<List<Double>> {
        val parsedMatrix = try {
            data.map { line ->
                line.replace(',', '.').split("""\s+""".toRegex()).map { it.toDouble()}
            }
        } catch (ex: Exception) {
            throw MatrixReaderExceptions.MatrixParsedException()
        }

        parsedMatrix.forEach {
            if (it.size - 1 != parsedMatrix.size) {
                throw MatrixReaderExceptions.MatrixSizeException()
            }
        }

        if (parsedMatrix.isEmpty()) {
            throw MatrixReaderExceptions.EmptyMatrix()
        }
        return parsedMatrix
    }

    fun getParsedMatrixFromFile(fileName: String): Matrix {
        val (data, inaccuracy) = readFromFile(fileName)
        val parsedData = getParsedData(data)
        return Matrix(matrix = parsedData, inaccuracy = inaccuracy)
    }

    fun getParsedMatrixFromConsole(): Matrix {
        val (data, inaccuracy) = readFromConsole()
        val parsedData = getParsedData(data)
        return Matrix(matrix = parsedData, inaccuracy = inaccuracy)
    }
}
