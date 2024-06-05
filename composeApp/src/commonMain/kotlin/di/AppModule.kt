package di

import features.endpoints.*
import features.endpoints.json_placeholder.*
import org.koin.dsl.*

fun appModule() = module {
    single { JSONPlaceholderApi() }
    single { JSONPlaceholderRepository(get()) }
}
