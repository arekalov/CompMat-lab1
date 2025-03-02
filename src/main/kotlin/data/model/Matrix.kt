package data.model


import ui.io.MatrixReaderExceptions
import kotlin.math.abs

data class Matrix(
    val matrix: List<List<Double>>,
    val inaccuracy: Double,
) {
    val rowsCount = matrix.size
    val colsCount = matrix[0].size

    private val _approachCalculateFuns: MutableList<(Approach, Approach) -> Double> = mutableListOf()
    val approachCalculateFuns: List<(Approach, Approach) -> Double>
        get() = _approachCalculateFuns

    init {
        val diagonallyDominantMatrix = findDiagonallyDominantMatrix(matrix)
            ?: throw MatrixReaderExceptions.NotDiagonallyDominantException()

        for (i in 0..<rowsCount) {
            _approachCalculateFuns.add { lastApproach: Approach, nowApproach: Approach ->
                var sum = 0.0
                for (j in 0..<colsCount - 1) {
                    if (i == j) continue

                    sum += if (i < j) {
                        lastApproach.approachVector[j] * -diagonallyDominantMatrix[i][j]
                    } else {
                        nowApproach.approachVector[j] * -diagonallyDominantMatrix[i][j]
                    }
                }
                sum += diagonallyDominantMatrix[i].last()
                sum /= diagonallyDominantMatrix[i][i]
                sum
            }
        }
    }

    private fun findDiagonallyDominantMatrix(matrix: List<List<Double>>): List<List<Double>>? {
        val permutations = matrix.indices.toList().permutations()

        perm@ for (perm in permutations) {
            val permutedMatrix = perm.map { matrix[it] }
            for (i in permutedMatrix.indices) {
                val diag = abs(permutedMatrix[i][i])
                val otherSum = permutedMatrix[i].dropLast(1).mapIndexed { idx, it ->
                    if (idx != i) abs(it) else 0.0
                }.sum()

                if (diag <= otherSum) {
                    continue@perm
                }
            }
            return permutedMatrix
        }
        return null
    }

    private fun <T> List<T>.permutations(): List<List<T>> {
        if (size <= 1) return listOf(this)
        val result = mutableListOf<List<T>>()
        for (i in indices) {
            val element = this[i]
            val rest = this.filterIndexed { index, _ -> index != i }
            for (perm in rest.permutations()) {
                result.add(listOf(element) + perm)
            }
        }
        return result
    }
}
