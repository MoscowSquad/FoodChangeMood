package di

import org.example.data.GymHelperController
import org.example.logic.usecases.*
import org.koin.dsl.module

val useCaseModule = module {
    single { GetHealthyFastFoodMealsUseCase(get()) }
    single { SearchMealByNameUseCase(get(), get()) }
    single { GetIraqiMealsUseCase(get()) }
    single { EasyFoodSuggestionUseCase(get()) }
    single { RandomMealNameProvider(get()) }
    single { SweetsWithNoEggUseCase(get()) }
    single { GetKetoDietMealUseCase(get()) }
    single { GetMealsByDateUseCase(get()) }
    single { GetMealByIdUseCase(get()) }
    single { GymHelperController(get()) }
    single { GetGymMealsUseCase(get()) }
    single { SearchMealByCountryUseCase(get()) }
    single { SearchMealByNameUseCase(get(), get()) }
    single { GetIngredientMealsUseCase(get()) }
    single { GetRandomMealsHavePotatoesUseCase(get()) }
    single { GetHighCaloriesMealsUseCase(get()) }
    single { GetSeafoodByProteinContentUseCase(get()) }
    single { FindItalianMealsForLargeGroupsUseCase(get()) }
}
