package data

import data.model.Approach
import data.model.Matrix
import data.model.Result
import kotlin.math.abs

class Repository {
    private fun calculateZeroApproach(matrix: Matrix): List<Double> {
        val zeroApproachVector = mutableListOf<Double>()
        for (i in matrix.matrix.indices) {
            zeroApproachVector.add(matrix.matrix[i][i] / matrix.matrix[i].last())
        }
        return zeroApproachVector
        TODO("Добавить проверку при делении на ноль")
    }

    private fun swapApproaches(
        matrix: Matrix,
        nowApproach: Approach
    ): Pair<Approach, Approach> {
        val lastApproachNew = nowApproach.copy()
        val nowApproachNew = Approach.emptyApproach(matrix.rowsCount)
        return lastApproachNew to nowApproachNew
    }

    private fun isCurrentAccuracyAcceptable(
        acceptableInaccuracy: Double,
        nowApproach: Approach
    ): Boolean {
        return nowApproach.inaccuraciesVector.all { it <= acceptableInaccuracy }
    }

    private fun updateInaccuraciesVector(
        matrix: Matrix,
        lastApproach: Approach,
        nowApproach: Approach
    ) {
        val newNowInaccuraciesVector = mutableListOf<Double>()
        for (i in 0..<matrix.rowsCount) {
            newNowInaccuraciesVector.add(
                abs(nowApproach.approachVector[i] - lastApproach.approachVector[i]) /
                        abs(nowApproach.approachVector[i])
            )
        }
    }

    private fun iterate(
        matrix: Matrix,
        lastApproach: Approach,
        nowApproach: Approach
    ) {
        for (i in 0..<matrix.rowsCount) {
            matrix.approachCalculateFuns[i].invoke(lastApproach, nowApproach)
            nowApproach.approachVector
        }
        updateInaccuraciesVector(
            matrix = matrix,
            lastApproach = lastApproach,
            nowApproach = nowApproach
        )
    }

    fun calculateEquation(
        matrix: Matrix,
        acceptableInaccuracy: Double = ACCEPTABLE_INACCURACY,
        iterationLimit: Int = ITERATION_LIMIT,
    ): Result {
        var lastApproach = Approach(
            approachVector = calculateZeroApproach(matrix),
            inaccuraciesVector = List(matrix.rowsCount) { 100.0 }
        )

        var nowApproach = Approach(
            approachVector = List(matrix.rowsCount) { 0.0 },
            inaccuraciesVector = List(matrix.rowsCount) { 100.0 }
        )

        val iterationsList = mutableListOf<Approach>()
        for (i in 0..<iterationLimit) {
            iterate(
                matrix = matrix,
                nowApproach = nowApproach,
                lastApproach = lastApproach
            )
            updateInaccuraciesVector(
                matrix = matrix,
                lastApproach = lastApproach,
                nowApproach = nowApproach
            )
            iterationsList.add(nowApproach)
            if (isCurrentAccuracyAcceptable(
                    acceptableInaccuracy = acceptableInaccuracy,
                    nowApproach = nowApproach
                )
            ) {
                break
            }
            lastApproach = nowApproach.copy()
            nowApproach = Approach.emptyApproach(matrix.rowsCount)
        }
        return Result(rows = iterationsList)
    }

    companion object {
        const val ITERATION_LIMIT = 100
        const val ACCEPTABLE_INACCURACY = 0.01
    }
}
