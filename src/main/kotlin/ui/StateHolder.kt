package ui

import data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.*

class StateHolder(
    private val repository: Repository,
) {
    private var _state: MutableStateFlow<State> = MutableStateFlow(State.Input())
        set(value) {
            println(value)
            field = value
        }
    val state: StateFlow<State>
        get() = _state

    private val defaultCoroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)

    fun reduce(event: Event) {
        when (event) {
            is Event.ChangeInputType -> onChangeInputTypeEventReceived(event)

            is Event.Calculate -> onCalculateEventReceived()

            is Event.Quit -> onQuitEventReceived()

            is Event.Nothing -> {/* do nothing */ }
        }
    }

    private fun onQuitEventReceived() {
        _state.value = State.Off
    }

    private fun onCalculateEventReceived() {
        defaultCoroutineScope.launch {
            val result = repository.calculateEquation(
                matrix = (state.value as State.Work).matrix
            )
            _state.value = (state.value as State.Work).copy(result = result)
        }
    }

    private fun onChangeInputTypeEventReceived(event: Event.ChangeInputType) {
        _state.value = (_state.value as State.Input).copy(
            inputType = event.inputType
        )
    }
}
