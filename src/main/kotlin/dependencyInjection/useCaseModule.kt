package org.example.dependencyInjection

import org.example.logic.GetHealthyFastFoodMealsUseCase
import org.example.logic.KetoDietMealHelper
import org.example.logic.SearchMealByNameUseCase
import org.example.logic.SweetsWithNoEggUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { SearchMealByNameUseCase(get(), get()) }
    single { SweetsWithNoEggUseCase(get()) }
    single { GetHealthyFastFoodMealsUseCase(get()) }
    single { KetoDietMealHelper(get()) }

}
