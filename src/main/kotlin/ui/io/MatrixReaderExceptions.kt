package ui.io

sealed class MatrixReaderExceptions(override val message: String) : Exception() {
    class IncorrectFileName: MatrixReaderExceptions("Error: incorrect file name, repeat")
    class IncorrectMatrixSize: MatrixReaderExceptions("Error: incorrect rows count, repeat")

    class MatrixParsedException : MatrixReaderExceptions("Error: unexpected symbols in given matrix")
    class MatrixSizeException: Exception("Error: incorrect matrix size")
    class EmptyMatrix: Exception("Error: emptyMatrix")
}
