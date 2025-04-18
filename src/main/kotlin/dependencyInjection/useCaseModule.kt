package org.example.dependencyInjection

import org.example.logic.GetRandomMealsHavePotatoes
import org.example.logic.SearchMealByNameUseCase
import org.example.logic.SweetsWithNoEggUseCase
import org.koin.dsl.module
import org.example.logic.GetHealthyFastFoodMealsUseCase
import org.example.logic.SearchMealByNameUseCase
val useCaseModule = module {
    single { SearchMealByNameUseCase(get(), get()) }
    single { GetRandomMealsHavePotatoes(get()) }
    single { SweetsWithNoEggUseCase(get()) }
    single { GetHealthyFastFoodMealsUseCase(get()) }
    single { GetSeafoodByProteinContent(get()) }
}
