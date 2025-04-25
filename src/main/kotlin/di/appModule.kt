package di

import org.example.data.FuzzySearchMatcher
import org.example.data.KMPSearchMatcher
import org.example.data.MealRepositoryImpl
import org.example.logic.repository.MealRepository
import org.example.logic.repository.SearchMatcher
import org.example.utils.CsvParser
import org.koin.dsl.module
import java.io.File
import java.net.URI

val appModule = module {
    single<URI> { requireNotNull(javaClass.classLoader.getResource("food.csv")).toURI() }
    single<File> { File(get<URI>()) }
    single { CsvParser(get()) }
    single<MealRepository> { MealRepositoryImpl(get()) }
    single { FuzzySearchMatcher() }
    single { KMPSearchMatcher() }
    single<SearchMatcher> { KMPSearchMatcher() }
}