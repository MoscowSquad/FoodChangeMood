package di

import org.example.presentation.*
import org.example.presentation.io.ConsoleIO
import org.example.presentation.io.ConsoleIOImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import java.util.*

val presentationModule = module {
    single { Scanner(System.`in`) }
    single<ConsoleIO> { ConsoleIOImpl(get()) }
    factory { HealthyFastFoodMealsUI(get(), get()) }
    factory { SearchMealByNameUI(get(), get()) }
    factory { IraqiMealsUI(get(), get()) }
    factory { EasyFoodSuggestionUI(get(), get()) }
    factory { GuessGameUI(get(), get()) }
    factory { SweetsWithNoEggUI(get(), get()) }
    factory { KetoDietMealHelperUI(get(), get()) }
    factory { SearchMealsByDateUI(get(), get(), get()) }
    factory { GymHelperUI(get(), get()) }
    factory { SearchMealByCountryUI(get(), get()) }
    factory { IngredientGameUI(get(), get()) }
    factory { ILovePotatoUI(get(), get()) }
    factory { HighCaloriesMealsUI(get(), get()) }
    factory { GetSeaFoodMealsUI(get(), get()) }
    factory { FindItalianMealsForLargeGroupsUI(get(), get()) }

    factoryOf(::ConsoleFoodChangeMoodUI)
}