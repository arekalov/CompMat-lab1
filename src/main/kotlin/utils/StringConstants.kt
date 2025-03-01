package utils


enum class StringConstants(val msg: String) {
    // Messages
    DIVIDER_MSG("-------------\n"),
    INPUT_COMMAND_MSG("Input your command!"),
    GREETING_MSG("Hello, this is System of Linear Equations calculator!"),
    HELP_MSG("Menu: \nhelp - to see this text again \nmode <file/console> - select input mode \ncalculate - start solving system \nquit - bye, bye"),
    BYE_MSG("Bye, bye!"),
    CALCULATION_MSG("Calculation in progress, pleas wait!"),
    INPUT_TYPE_CHANGED_MSG("Input mode successfully changed"),

    // Errors
    ARG_COUNT_ERROR("Error count of argument, see menu to learn program commands and try again"),
    EMPTY_COMMAND_ERROR("Empty command, try again"),
    INVALID_COMMAND_ERROR("Invalid command, try again"),
    INVALID_INPUT_TYPE_ERROR("Invalid input type, select from <file/console> and try again"),
}
