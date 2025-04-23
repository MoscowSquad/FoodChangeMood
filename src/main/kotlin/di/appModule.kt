package di

import org.example.data.FuzzySearchMatcher
import org.example.data.KMPSearchMatcher
import org.example.data.MealRepositoryImpl
import org.example.logic.repository.MealRepository
import org.example.logic.repository.SearchMatcher
import org.example.utils.CsvParser
import org.koin.dsl.module

val appModule = module {
    single { CsvParser("food.csv") }
    single<MealRepository> { MealRepositoryImpl(get()) }
    single { FuzzySearchMatcher() }
    single { KMPSearchMatcher() }
    single<SearchMatcher> { KMPSearchMatcher() }
}