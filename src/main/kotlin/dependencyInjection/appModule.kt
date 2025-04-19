package dependencyInjection

import org.example.data.FuzzySearchMatcher
import org.example.data.KMPSearchMatcher
import org.example.logic.MealRepository
import org.example.logic.MealRepositoryImpl
import org.example.utils.CustomParser
import org.koin.dsl.module

val appModule = module {
    single { CustomParser("food.csv") }
    single<MealRepository> { MealRepositoryImpl(get()) }
    single { FuzzySearchMatcher() }
    single { KMPSearchMatcher() }
}