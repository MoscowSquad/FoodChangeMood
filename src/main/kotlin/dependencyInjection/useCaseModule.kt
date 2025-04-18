package org.example.dependencyInjection

import logic.EasyFoodSug
import org.example.logic.*
import org.koin.dsl.module

val useCaseModule = module {
    single { SearchMealByNameUseCase(get(), get()) }
    single { GetRandomMealsHavePotatoes(get()) }
    single { SweetsWithNoEggUseCase(get()) }
    single { GetHealthyFastFoodMealsUseCase(get()) }
    single { GetSeafoodByProteinContent(get()) }
    single { EasyFoodSug(get()) }
    single { IngredientGame(get()) }

}
