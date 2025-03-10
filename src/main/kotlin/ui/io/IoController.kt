package ui.io

import ui.Event
import ui.State
import utils.StringConstants
import utils.generateMatrixAssetFile

class IoController(private val matrixReader: MatrixReader) {
    fun reduce(currentState: State.Input): Event {
        printMessage(StringConstants.INPUT_COMMAND_MSG)
        return try {
            val inputText = readln().split(" ").map { it.lowercase() }
            if (inputText.isEmpty()) {
                printMessage(constant = StringConstants.EMPTY_COMMAND_ERROR, needToPrintDivider = true)
            }
            when (inputText.first()) {
                "quit" -> quit()

                "calc" -> calculate(inputType = currentState.inputType)

                "help" -> help()

                "chmod" -> chmod(args = inputText)

                "mode" -> getMod(currentInputType = currentState.inputType)

                "gen" -> generateRandom(args = inputText)

                else -> unexpectedCommand()
            }
        } catch (ex: RuntimeException) {
            quit()
        }
    }

    private fun generateRandom(args: List<String>): Event {
        try {
            val n = args[1].toInt()
            val path = "/Users/arekalov/Itmo/4/CompMat/CompMat-lab1/src/main/resources"
            val fileName = "genAsset"
            generateMatrixAssetFile(filename = fileName, n = n, filePath = path)
            return chmod(args = listOf("chmod", "file", "$path/$fileName"))
        } catch (ex: Exception) {
            printMessage(constant = StringConstants.INVALID_ARGUMENT_COUNT_ERROR, needToPrintDivider = true)
            return Event.Nothing
        }
    }

    private fun getMod(currentInputType: InputType): Event {
        printMessage(
            StringConstants.CURRENT_INPUT_TYPE,
            needToPrintDivider = true,
            otherToPrint = arrayOf(currentInputType.toString())
        )
        return Event.Nothing
    }

    private fun unexpectedCommand(): Event {
        printMessage(constant = StringConstants.INVALID_COMMAND_ERROR, needToPrintDivider = true)
        return Event.Nothing
    }

    private fun help(): Event {
        printMessage(constant = StringConstants.HELP_MSG, needToPrintDivider = true)
        return Event.Nothing
    }

    private fun quit() = Event.Quit

    private fun calculate(inputType: InputType): Event {
        return try {
            val matrixList = when (inputType) {
                is InputType.Console -> matrixReader.getParsedMatrixFromConsole()

                is InputType.File -> matrixReader.getParsedMatrixFromFile(fileName = inputType.filePath)
            }
            Event.Calculate(matrix = matrixList)
        } catch (ex: MatrixReaderExceptions) {
            println(ex.message)
            printMessage(StringConstants.DIVIDER_MSG)
            Event.Nothing

        }
    }

    private fun chmod(args: List<String>): Event {
        if (args.size < 2) {
            printMessage(StringConstants.ARG_COUNT_ERROR, needToPrintDivider = true)
            return Event.Nothing
        }

        return if (args[1] == InputType.File.STRING_TYPE && args.size == 3) {
            printMessage(constant = StringConstants.INPUT_TYPE_CHANGED_MSG, needToPrintDivider = true)
            Event.ChangeInputType(InputType.File(filePath = args[2]))
        } else if (args[1] == InputType.Console.STRING_TYPE && args.size == 2) {
            printMessage(constant = StringConstants.INPUT_TYPE_CHANGED_MSG, needToPrintDivider = true)
            Event.ChangeInputType(InputType.Console())
        } else {
            printMessage(StringConstants.ARG_COUNT_ERROR, needToPrintDivider = true)
            Event.Nothing
        }
    }

    fun printGreeting() {
        printMessage(StringConstants.GREETING_MSG)
        println()
        println(StringConstants.HELP_MSG.msg)
    }

    companion object {
        fun printMessage(
            constant: StringConstants,
            needToPrintDivider: Boolean = false,
            vararg otherToPrint: String
        ) {
            println(constant.msg + otherToPrint.joinToString(" "))
            if (needToPrintDivider) {
                println(StringConstants.DIVIDER_MSG.msg)
            }
        }
    }
}
