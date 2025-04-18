package org.example

import org.example.dependencyInjection.appModule
import org.example.dependencyInjection.useCaseModule
import org.example.logic.SearchMealByNameUseCase
import org.example.model.BlankKeywordException
import org.example.model.KeywordNotFoundException
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform
import org.example.utils.CustomParser


fun main() {
    startKoin {
        modules(appModule, useCaseModule)
    }
    val searchUseCase: SearchMealByNameUseCase = KoinPlatform.getKoin().get()

    try {
        searchUseCase.search("ed winter squash mexi")
            .also { println(it) }
    } catch (e: KeywordNotFoundException) {
        println(e.message)
    } catch (e: BlankKeywordException) {
        println("You should enter country name")
    }
}

fun mainParsingTest(){
    val parser = CustomParser()
    val file = parser.getResourceFile("food.csv")
    val list = parser.parseMealsCsv(file)
    list.take(10).forEach { item ->
        println(item.toString())
    }
}