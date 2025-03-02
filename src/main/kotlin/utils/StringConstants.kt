package utils


enum class StringConstants(val msg: String) {
    // Messages
    DIVIDER_MSG("-------------\n"),
    INPUT_COMMAND_MSG("Input your command!"),
    GREETING_MSG("Hello, this is System of Linear Equations calculator!"),
    HELP_MSG(
        "Menu: \n1) help - to see this text again \n2) chmod <file filepath | console> - select input mode\n" +
                "3) mode - get current mode\n4) calc - start solving system \n5) quit - bye, bye"
    ),
    BYE_MSG("Bye, bye!"),
    CALCULATION_MSG("Calculation in progress, pleas wait!"),
    INPUT_TYPE_CHANGED_MSG("Input mode successfully changed"),
    CURRENT_INPUT_TYPE("Current input mode: "),
    RESULT_READY_MSG("Result ready"),
    INPUT_ROW_COUNT_MSG("Input row count (mandatory) and inaccuracy (optional) separated by a space"),
    INPUT_ROWS_MS("Enter the rows of the matrix one by one separated by a space"),

    // Errors
    ARG_COUNT_ERROR("Error count of argument, see menu to learn program commands and try again"),
    EMPTY_COMMAND_ERROR("Empty command, try again"),
    INVALID_COMMAND_ERROR("Invalid command, try again"),
    INVALID_INPUT_TYPE_ERROR("Invalid input type, select from <file/console> and try again"),
}
