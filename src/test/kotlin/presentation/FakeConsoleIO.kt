package presentation

import org.example.presentation.io.ConsoleIO
import java.util.*

class FakeConsoleIO(
    val inputs: Queue<String>,
    val outputs: MutableList<String?> = mutableListOf()
) : ConsoleIO {
    override fun read(): String = inputs.poll()
    override fun write(message: String?) {
        outputs.add(message)
    }
}
