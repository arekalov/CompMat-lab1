package data.model


data class Matrix(
    val matrix: List<List<Double>>,
) {
    val rowsCount = matrix.size
    val colsCount = matrix[0].size

    private val _approachCalculateFuns: MutableList<(Approach, Approach) -> Double> = mutableListOf()
    val approachCalculateFuns: List<(Approach, Approach) -> Double>
        get() = _approachCalculateFuns

    init {
        for (i in 0..<rowsCount) {
            _approachCalculateFuns.add { lastApproach: Approach, nowApproach: Approach ->
                var sum = 0.0
                for (j in 0..<colsCount - 1) {
                    if (i == j) {
                        continue
                    }
                    sum += if (i < j) {
                        lastApproach.approachVector[j] * -matrix[i][j]
                    } else {
                        nowApproach.approachVector[j] * -matrix[i][j]
                    }
                }
                sum += matrix[i].last()
                sum /= matrix[i][i]
                sum
            }
        }
    }
}
