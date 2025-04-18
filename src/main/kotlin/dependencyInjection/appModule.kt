package org.example.dependencyInjection

import com.moscow.squad.FuzzySearchMatcher
import com.moscow.squad.KMPSearchMatcher
import org.example.logic.SearchMatcher
import org.example.data.MockDataRepository
import org.example.logic.MealRepository
import org.koin.dsl.module

val appModule = module {
    single<MealRepository> { MockDataRepository() }
    single<SearchMatcher> { KMPSearchMatcher() }
}