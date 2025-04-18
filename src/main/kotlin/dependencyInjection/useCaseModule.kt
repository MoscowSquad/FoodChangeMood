package org.example.dependencyInjection

import org.example.logic.SearchMealByNameUseCase
import org.example.usecases.SweetsWithNoEggUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { SearchMealByNameUseCase(get(), get()) }
    single { SweetsWithNoEggUseCase(get()) }
}
