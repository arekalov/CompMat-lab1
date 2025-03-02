import data.Repository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import ui.Event
import ui.State
import ui.StateHolder
import ui.io.IoController
import ui.io.MatrixReader
import utils.StringConstants

class Main {
    private val repository = Repository()
    private val stateHolder = StateHolder(repository = repository)

    private val matrixReader = MatrixReader()
    private val ioController = IoController(matrixReader = matrixReader)

    fun run() = runBlocking {
        ioController.printGreeting()
        launch(Dispatchers.IO) {
            stateHolder.state.collectLatest { state ->
                when (state) {
                    is State.Off -> {
                        IoController.printMessage(StringConstants.BYE_MSG)
                        cancel()
                    }

                    is State.Input -> {
                        while (isActive && stateHolder.state.value is State.Input) {
                            val action = ioController.reduce(stateHolder.state.value as State.Input)
                            stateHolder.reduce(action)
                        }
                    }

                    is State.Work -> {
                        if (state.result == null) {
                            IoController.printMessage(StringConstants.CALCULATION_MSG)
                        } else {
                            println(state.result)
                            IoController.printMessage(StringConstants.RESULT_READY_MSG, needToPrintDivider = true)
                            stateHolder.reduce(Event.SetInputState)
                        }
                    }
                }
            }
        }
    }
}

fun main() {
    Main().run()
}
