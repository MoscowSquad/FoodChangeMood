package org.example.dependencyInjection

import org.example.data.MockDataRepository
import org.example.data.RandomMealNameImpl
import org.example.logic.MealRepository
import org.example.logic.userInputGuessImpl
import org.koin.dsl.module
import org.example.data.RandomMealName
val appModule = module {
    single<MealRepository> { MockDataRepository() }
    single<RandomMealName> { RandomMealNameImpl(get()) }
    single<userInputGuessImpl> { userInputGuessImpl() }
}