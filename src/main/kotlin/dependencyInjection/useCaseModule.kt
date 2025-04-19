package org.example.dependencyInjection

import logic.EasyFoodSuggestionUseCase
import org.example.data.GymHelperController
import org.example.logic.*
import org.koin.dsl.module

val useCaseModule = module {
    single { SearchMealByNameUseCase(get(), get()) }
    single { GetRandomMealsHavePotatoesUseCase(get()) }
    single { SweetsWithNoEggUseCase(get()) }
    single { GetHealthyFastFoodMealsUseCase(get()) }
    single { GetKetoDietMealUseCase(get()) }
    single { GetSeafoodByProteinContentUseCase(get()) }
    single { GetMealsByDateUseCase(get()) }
    single { GetMealByIdUseCase(get()) }
    single { SearchMealByCountryUseCase(get()) }
    single { FindItalianMealsForLargeGroupsUseCase(get()) }
    single { EasyFoodSuggestionUseCase(get()) }
    single { GetIngredientMealsUseCase(get()) }
    single{ RandomMealNameProvider(get()) }
    single { GetIraqiMealsUseCase(get()) }
    single { GymHelperUseCase(get()) }
    single { GetHighCaloriesMealsUseCase(get()) }

}
