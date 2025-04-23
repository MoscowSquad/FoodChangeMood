package di

import org.example.presentation.*
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val presentationModule = module {
    factory { HealthyFastFoodMealsUI(get()) }
    factory { SearchMealByNameUI(get()) }
    factory { IraqiMealsUI(get()) }
    factory { EasyFoodSuggestionUI(get()) }
    //factory { GuessGameUI(get()) }
    factory { SweetsWithNoEggUI(get()) }
    factory { KetoDietMealHelperUI(get()) }
    factory { SearchMealsByDateUI(get(), get()) }
    factory { GymHelperUI(get()) }
    factory { SearchMealByCountryUI(get()) }
    factory { IngredientGameUI(get()) }
    factory { ILovePotatoUI(get()) }
    factory { HighCaloriesMealsUI(get()) }
    factory { GetSeaFoodMealsUI(get()) }
    factory { FindItalianMealsForLargeGroupsUI(get()) }

    //factoryOf(::ConsoleFoodChangeMoodUI)
}