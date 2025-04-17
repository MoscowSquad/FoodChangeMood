package org.example.dependencyInjection

import com.moscow.squad.FuzzySearchMatcher
import org.example.logic.SearchMatcher
import org.koin.dsl.module

val appModule = module {
    single<SearchMatcher> { FuzzySearchMatcher(2) }
}