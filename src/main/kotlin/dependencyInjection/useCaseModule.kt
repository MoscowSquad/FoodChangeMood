package org.example.dependencyInjection

import org.example.logic.GetSeafoodByProteinContent
import org.koin.core.module.Module
import org.koin.dsl.module
import org.example.logic.SearchMealByNameUseCase
val useCaseModule: Module = module {  
    single { SearchMealByNameUseCase(get(), get()) }
    single { GetSeafoodByProteinContent(get()) }
}
