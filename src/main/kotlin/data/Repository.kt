package data

import data.model.Approach
import data.model.Matrix
import data.model.Result
import kotlin.math.abs

class Repository(
    private val matrix: Matrix,
    private val acceptableInaccuracy: Double = ACCEPTABLE_INACCURACY,
    private val iterationLimit: Int = ITERATION_LIMIT,
) {

    private var lastApproach = Approach(
        approachVector = calculateZeroApproach(matrix),
        inaccuraciesVector = List(matrix.rowsCount) { 100.0 }
    )

    private var nowApproach = Approach(
        approachVector = List(matrix.rowsCount) { 0.0 },
        inaccuraciesVector = List(matrix.rowsCount) { 100.0 }
    )

    private fun calculateZeroApproach(matrix: Matrix): List<Double> {
        val zeroApproachVector = mutableListOf<Double>()
        for (i in matrix.matrix.indices) {
            zeroApproachVector.add(matrix.matrix[i][i] / matrix.matrix[i].last())
        }
        return zeroApproachVector
        TODO("Добавить проверку при делении на ноль")
    }

    private fun swapApproaches() {
        lastApproach = nowApproach.copy()
        nowApproach = Approach.emptyApproach(matrix.rowsCount)
    }

    private fun isCurrentAccuracyAcceptable(): Boolean {
        return nowApproach.inaccuraciesVector.all { it <= acceptableInaccuracy }
    }

    private fun updateInaccuraciesVector() {
        val newNowInaccuraciesVector = mutableListOf<Double>()
        for (i in 0..<matrix.rowsCount) {
            newNowInaccuraciesVector.add(
                abs(nowApproach.approachVector[i] - lastApproach.approachVector[i]) /
                        abs(nowApproach.approachVector[i])
            )
        }
    }

    private fun iterate() {
        for (i in 0..<matrix.rowsCount) {
            val nowApproachI = matrix.approachCalculateFuns[i].invoke(lastApproach, nowApproach)
            nowApproach.approachVector
        }
        updateInaccuraciesVector()
    }

    fun calculateEquation(): Result {
        val iterationsList = mutableListOf<Approach>()
        for (i in 0..<iterationLimit) {
            iterate()
            updateInaccuraciesVector()
            iterationsList.add(nowApproach)
            if (isCurrentAccuracyAcceptable()) {
                break
            }
            swapApproaches()
        }
        return Result(rows = iterationsList)
    }

    companion object {
        const val ITERATION_LIMIT = 100
        const val ACCEPTABLE_INACCURACY = 0.01
    }
}
