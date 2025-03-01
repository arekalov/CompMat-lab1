import data.Repository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
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
        IoController.printMessage(StringConstants.GREETING_MSG)
        launch(Dispatchers.IO) {
            stateHolder.state.collectLatest { state ->
                when (state) {
                    is State.Off -> {
                        IoController.printMessage(StringConstants.BYE_MSG)
                        cancel()
                    }

                    is State.Input -> {
                        while (isActive && stateHolder.state.value is State.Input) {
                            val action = ioController.reduce()
                            stateHolder.reduce(action)
                        }
                    }

                    is State.Work -> {
                        IoController.printMessage(StringConstants.CALCULATION_MSG)
                    }
                }
            }
        }
    }
}

fun main() {
    Main().run()
}