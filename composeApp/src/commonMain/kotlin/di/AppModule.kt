package di

import features.endpoints.*
import features.endpoints.json_placeholder.*
import features.settings.*
import org.koin.dsl.*

fun appModule() = module {
    single { SettingsStore() }
    single { JSONPlaceholderApi() }
    single { JSONPlaceholderRepository(get()) }
}
