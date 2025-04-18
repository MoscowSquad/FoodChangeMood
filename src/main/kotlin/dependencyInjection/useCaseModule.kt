package org.example.dependencyInjection
import org.example.logic.SearchMealByNameUseCase
import org.example.logic.SweetsWithNoEggUseCase
import org.example.logic.GetHealthyFastFoodMealsUseCase
import org.koin.dsl.module
import org.example.data.MockDataRepository
import org.example.logic.RandomMealNameProvider
import org.example.logic.UserInputGuessImpl


val useCaseModule = module {
    single { SearchMealByNameUseCase(get(), get()) }
    single { SweetsWithNoEggUseCase(get()) }
    single { GetHealthyFastFoodMealsUseCase(get()) }
    single{ MockDataRepository() }
    single{ RandomMealNameProvider(get()) }
    single{ UserInputGuessImpl() }
}