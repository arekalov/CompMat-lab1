package data.model

data class Approach(
    val approachVector: List<Double>,
    val inaccuraciesVector: List<Double>,
) {
    companion object {
        fun emptyApproach(size: Int) =
            Approach(
                approachVector = List(size) { 0.0 },
                inaccuraciesVector = List(size) { 100.0 }
            )
    }
}
