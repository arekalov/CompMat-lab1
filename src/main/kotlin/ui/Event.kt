package ui

import data.model.Matrix

sealed class Event {
    data object Nothing : Event()

    data object Quit : Event()

    data class Calculate(
        val matrix: Matrix
    ) : Event()

    data class ChangeInputType(
        val inputType: InputType
    ) : Event()
}

enum class InputType {
    File,
    Console,
}