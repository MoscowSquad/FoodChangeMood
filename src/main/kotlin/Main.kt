package org.example

import com.moscow.squad.FuzzySearchMatcher
import org.example.data.SearchExecutor
import org.example.dependencyInjection.appModule
import org.example.logic.SearchMatcher
import org.example.model.BlankKeywordException
import org.example.model.KeywordNotFoundException
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform

fun main() {
    startKoin {
        modules(appModule)
    }
    val searchMatcher: SearchMatcher = KoinPlatform.getKoin().get()

    val searchExecutor = SearchExecutor(
        searchMatcher,
        listOf(
            "Yemen",
            "North Korea",
            "South Korea",
            "China",
            "Cairo",
            "Egypt",
        )
    )

    try {
        searchExecutor.search("Cairsso")
            .also { println(it) }
    } catch (e: KeywordNotFoundException) {
        println(e.message)
    } catch (e: BlankKeywordException) {
        println("You should enter country name")
    }
}