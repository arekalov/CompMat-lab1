package ui

import data.model.Matrix
import data.model.Result
import ui.io.InputType

sealed class State {
    data object Off : State()

    data class Work(
        val matrix: Matrix,
        val result: Result? = null,
    ) : State()

    data class Input(
        val inputType: InputType = InputType.Console()
    ) : State()
}
