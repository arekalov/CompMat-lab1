package ui.io


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
