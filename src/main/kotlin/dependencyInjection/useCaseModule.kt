package org.example.dependencyInjection

import org.example.logic.GetSeafoodByProteinContent
import org.koin.core.module.Module
import org.koin.dsl.module

val useCaseModule: Module = module {
    single { GetSeafoodByProteinContent(get()) }
}
