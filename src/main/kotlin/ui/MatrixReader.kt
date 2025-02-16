package ui

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

    private fun getParsedData(data: List<String>): MutableList<MutableList<Double>> {
        val parsedMatrix = try {
            data.map { line ->
                line.split(" ").map { it.toDouble() }.toMutableList()
            }.toMutableList()
        } catch (ex: Exception) {
            throw MatrixReaderExceptions.MatrixParsedException()
        }

        parsedMatrix.forEach {
            if (it.size - 1 != parsedMatrix.size) {
                throw MatrixReaderExceptions.MatrixSizeException()
            }
        }

        if (parsedMatrix.size == 0) {
            throw MatrixReaderExceptions.EmptyMatrix()
        }
        return parsedMatrix
    }

    fun getParsedMatrixFromFile(fileName: String): MutableList<MutableList<Double>> {
        return getParsedData(data = readFromFile(fileName = fileName))
    }

    fun getParsedMatrixFromConsole(): MutableList<MutableList<Double>> {
        return getParsedData(data = readFromConsole())
    }
}