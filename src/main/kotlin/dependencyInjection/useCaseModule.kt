package org.example.dependencyInjection

import org.example.logic.*
import org.koin.dsl.module

val useCaseModule = module {
    single { SearchMealByNameUseCase(get(), get()) }
    single { GetRandomMealsHavePotatoes(get()) }
    single { SweetsWithNoEggUseCase(get()) }
    single { GetHealthyFastFoodMealsUseCase(get()) }
    single { KetoDietMealHelper(get()) }
    single { GetSeafoodByProteinContent(get()) }
    single { GetMealsByDateUseCase(get()) }
    single { GetMealByIdUseCase(get()) }
    single { SearchMealByCountryUseCase(get()) }
    single { FindItalianMealsForLargeGroupsUseCase(get()) }
}
