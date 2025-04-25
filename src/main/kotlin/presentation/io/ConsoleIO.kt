package org.example.presentation.io

interface ConsoleIO {
    fun read(): String
    fun write(message: String?)
    fun showError(message: String?)
}