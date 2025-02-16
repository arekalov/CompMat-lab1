import ui.MatrixReader

fun main() {
    val reader = MatrixReader()
    val path = "/Users/arekalov/Itmo/4/CompMat/CompMat-lab1/src/main/resources/matrixAsset"
    val res = reader.getParsedMatrixFromFile(path)
    val res2 = reader.getParsedMatrixFromConsole()
    res.forEach { println(it) }
}