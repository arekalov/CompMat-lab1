package ui.io

import data.model.Matrix
import ui.Event
import ui.InputType
import ui.State
import utils.StringConstants

class IoController(private val matrixReader: MatrixReader) {
    fun reduce(currentState: State.Input): Event {
        val inputText = readln().split(" ").map { it.lowercase() }
        if (inputText.isEmpty()) {
            printMessage(constant = StringConstants.EMPTY_COMMAND_ERROR)
        }
        return when (inputText.first()) {
            "quit" -> quit()

            "calc" -> calculate(inputType = currentState.inputType)

            "help" -> help()

            "chmod" -> chmod(args = inputText)

            "mode" -> getMod(currentInputType = currentState.inputType)

            else -> unexpectedCommand()
        }
    }

    private fun getMod(currentInputType: InputType): Event {
        printMessage(StringConstants.CURRENT_INPUT_TYPE, otherToPrint = arrayOf(currentInputType.toString()))
        return Event.Nothing
    }

    private fun unexpectedCommand(): Event {
        printMessage(constant = StringConstants.INVALID_COMMAND_ERROR)
        return Event.Nothing
    }

    private fun help(): Event {
        printMessage(constant = StringConstants.HELP_MSG)
        return Event.Nothing
    }

    private fun quit() = Event.Quit

    private fun calculate(inputType: InputType): Event {
        return try {
            val matrixList = when (inputType) {
                is InputType.Console -> matrixReader.getParsedMatrixFromConsole()

                is InputType.File -> matrixReader.getParsedMatrixFromFile(fileName = inputType.filePath)
            }
            Event.Calculate(matrix = Matrix(matrixList))
        } catch (ex: MatrixReaderExceptions) {
            println(ex.message)
            printMessage(constant = StringConstants.INPUT_COMMAND_MSG)
            printMessage(constant = StringConstants.DIVIDER_MSG)
            Event.Nothing

        }
    }

    private fun chmod(args: List<String>): Event {
        if (args.size < 2) {
            printMessage(StringConstants.ARG_COUNT_ERROR)
            return Event.Nothing
        }

        return if (args[1] == InputType.File.STRING_TYPE && args.size == 3) {
            printMessage(constant = StringConstants.INPUT_TYPE_CHANGED_MSG)
            Event.ChangeInputType(InputType.File(filePath = args[2]))
        } else if (args[1] == InputType.Console.STRING_TYPE && args.size == 2) {
            printMessage(constant = StringConstants.INPUT_TYPE_CHANGED_MSG)
            Event.ChangeInputType(InputType.Console())
        } else {
            printMessage(StringConstants.ARG_COUNT_ERROR)
            Event.Nothing
        }
    }

    fun printGreeting() {
        printMessage(StringConstants.GREETING_MSG)
        println()
        println(StringConstants.HELP_MSG.msg)
        printMessage(StringConstants.INPUT_COMMAND_MSG)
    }

    companion object {
        fun printMessage(constant: StringConstants, vararg otherToPrint: String) {
            println(constant.msg + otherToPrint.joinToString(" "))
            if (constant !in listOf(
                    StringConstants.BYE_MSG,
                    StringConstants.GREETING_MSG,
                    StringConstants.INPUT_COMMAND_MSG,
                    StringConstants.CALCULATION_MSG,
                    StringConstants.RESULT_READY_MSG,
                    StringConstants.DIVIDER_MSG,
                )
            ) {
                println(StringConstants.DIVIDER_MSG.msg)
            }
        }
    }
}
