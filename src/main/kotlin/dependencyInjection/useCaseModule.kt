package org.example.dependencyInjection

import org.example.logic.GetHealthyFastFoodMealsUseCase
import org.koin.core.module.Module
import org.koin.dsl.module


val useCaseModule: Module = module{
    // Use cases
    single { GetHealthyFastFoodMealsUseCase(get()) }


}
