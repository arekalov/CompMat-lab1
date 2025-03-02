package ui

import data.model.Matrix

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

sealed interface InputType {
    val stringType: String

    class Console : InputType {
        override val stringType: String
            get() = STRING_TYPE

        companion object {
            const val STRING_TYPE = "console"
        }

        override fun toString(): String {
            return STRING_TYPE
        }
    }

    data class File(val filePath: String) : InputType {
        override val stringType: String
            get() = STRING_TYPE

        override fun toString(): String {
            return "$STRING_TYPE $filePath"
        }

        companion object {
            const val STRING_TYPE = "file"
        }
    }
}
