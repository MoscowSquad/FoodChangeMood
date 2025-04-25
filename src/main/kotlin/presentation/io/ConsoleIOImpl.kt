package org.example.presentation.io

import java.util.*

class ConsoleIOImpl(
    private val scanner: Scanner
) : ConsoleIO {
    override fun read(): String {
        return scanner.nextLine()
    }

    override fun write(message: String?) {
        println(message)
    }
}