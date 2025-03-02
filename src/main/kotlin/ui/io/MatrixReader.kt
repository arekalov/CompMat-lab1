package ui.io

import java.io.File
import java.nio.charset.Charset

class MatrixReader {
    private fun readFromFile(fileName: String, charset: Charset = Charsets.UTF_8): List<String> = try {
        File(fileName).readLines(charset = charset)
    } catch (ex: Exception) {
        throw MatrixReaderExceptions.IncorrectFileName()
    }

    private fun readFromConsole(): List<String> = try {
        val rowsCount = readln().toInt()
        val data = mutableListOf<String>()
        repeat(rowsCount) {
            data.add(readln())
        }
        data
    } catch (ex: Exception) {
        throw MatrixReaderExceptions.IncorrectMatrixSize()
    }

    private fun getParsedData(data: List<String>): List<List<Double>> {
        val parsedMatrix = try {
            data.map { line ->
                line.replace(',', '.').split(" ").map { it.toDouble() }
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

    fun getParsedMatrixFromFile(fileName: String): List<List<Double>> {
        return getParsedData(data = readFromFile(fileName = fileName))
    }

    fun getParsedMatrixFromConsole(): List<List<Double>> {
        return getParsedData(data = readFromConsole())
    }
}
