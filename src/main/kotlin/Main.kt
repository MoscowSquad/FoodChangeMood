package org.example

import dependencyInjection.appModule
import org.example.dependencyInjection.presentationModule
import org.example.dependencyInjection.useCaseModule
import org.example.presentation.ConsoleFoodChangeMoodUI
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin

fun main() {
    startKoin {
        modules(appModule, useCaseModule, presentationModule)
    }
    val foodConsole: ConsoleFoodChangeMoodUI = getKoin().get()
    foodConsole.start()
}
