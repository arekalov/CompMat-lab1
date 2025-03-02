package ui

import data.model.Matrix
import ui.io.InputType

sealed class Event {
    data object Nothing : Event()

    data object Quit : Event()

    data object SetInputState : Event()

    data class Calculate(
        val matrix: Matrix
    ) : Event()

    data class ChangeInputType(
        val inputType: InputType
    ) : Event()
}

