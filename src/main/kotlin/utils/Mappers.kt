package utils

import ui.InputType

fun String.toInputType(): InputType {
    if (this.lowercase() == InputType.File.name) {
        return InputType.File
    }
    return InputType.Console
}