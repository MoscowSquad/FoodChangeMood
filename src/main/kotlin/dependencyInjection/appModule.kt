package org.example.dependencyInjection

import org.example.data.MockDataRepository
import org.example.logic.MealRepository
import org.koin.dsl.module
import org.example.logic.KetoDietMealHelper
import org.example.utils.CsvParser
import org.example.utils.CsvParserImpl

val appModule = module {
    single<MealRepository> { MockDataRepository() }
    single<CsvParser> { CsvParserImpl() }
    single { KetoDietMealHelper(get()) }
}