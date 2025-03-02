package ui.io

sealed class MatrixReaderExceptions(override val message: String) : Exception() {
    class IncorrectFileName : MatrixReaderExceptions("Error: incorrect file name, repeat")
    class IncorrectMatrixSize : MatrixReaderExceptions("Error: incorrect rows count, repeat")

    class MatrixParsedException : MatrixReaderExceptions("Error: unexpected symbols in given matrix")
    class MatrixSizeException : MatrixReaderExceptions("Error: incorrect matrix size")
    class EmptyMatrix : MatrixReaderExceptions("Error: emptyMatrix")
    class NotDiagonallyDominantException :
        MatrixReaderExceptions("Error: cannot convert matrix to diagonally dominant form")
}
