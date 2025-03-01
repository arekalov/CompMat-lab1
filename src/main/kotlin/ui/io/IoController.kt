package ui.io

import ui.Event
import ui.InputType
import utils.StringConstants
import utils.toInputType

class IoController(private val matrixReader: MatrixReader) {
    fun reduce(): Event {
        printMessage(StringConstants.INPUT_COMMAND_MSG)
        val inputText = readln().split(" ").map { it.lowercase() }
        if (inputText.isEmpty()) {
            printMessage(constant = StringConstants.EMPTY_COMMAND_ERROR)
        }
        return when (inputText.first()) {
            "quit" -> quit()

            "calculate" -> calculate()

            "help" -> help()

            "mode" -> mode(args = inputText)

            else -> unexpectedCommand()
        }
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

    private fun calculate(): Event {
        return Event.Nothing
    }

    private fun mode(args: List<String>): Event {
        if (args.size != 2) {
            printMessage(StringConstants.ARG_COUNT_ERROR)
            return Event.Nothing
        }
        if (args[1] !in InputType.entries.map { it.name.lowercase() }) {
            printMessage(StringConstants.INVALID_INPUT_TYPE_ERROR)
            return Event.Nothing
        }

        val inputType = args[1].toInputType()
        printMessage(constant = StringConstants.INPUT_TYPE_CHANGED_MSG)
        return Event.ChangeInputType(inputType = inputType)
    }

    companion object {
        fun printMessage(constant: StringConstants) {
            println(constant.msg)
            if (constant !in listOf(
                    StringConstants.BYE_MSG,
                    StringConstants.GREETING_MSG,
                    StringConstants.INPUT_COMMAND_MSG
                )
            ) {
                println(StringConstants.DIVIDER_MSG.msg)
            }
        }
    }
}
