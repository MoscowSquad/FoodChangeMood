package org.example.dependencyInjection

import logic.EasyFoodSuggestionUseCase
import org.example.data.GymHelperController
import org.example.logic.*
import org.koin.dsl.module

val useCaseModule = module {
    single { GetHealthyFastFoodMealsUseCase(get()) } //1
    single { SearchMealByNameUseCase(get(), get()) } //2
    single { GetIraqiMealsUseCase(get()) } //3
    single { EasyFoodSuggestionUseCase(get()) } //4
    single { RandomMealNameProvider(get()) } //5
    single { SweetsWithNoEggUseCase(get()) } //6
    single { GetKetoDietMealUseCase(get()) } //7
    single { GetMealsByDateUseCase(get()) } //8
    single { GetMealByIdUseCase(get()) } //8
    single { GymHelperController(get()) } //9
    single { GetGymMealsUseCase(get()) } //9
    single { SearchMealByCountryUseCase(get()) } //10
    single { SearchMealByNameUseCase(get(), get()) } //10
    single { GetIngredientMealsUseCase(get()) } //11
    single { GetRandomMealsHavePotatoesUseCase(get()) } //12
    single { GetHighCaloriesMealsUseCase(get()) } //13
    single { GetSeafoodByProteinContentUseCase(get()) } //14
    single { FindItalianMealsForLargeGroupsUseCase(get()) } //15
}
