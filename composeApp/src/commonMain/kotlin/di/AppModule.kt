package di

import features.endpoints.*
import features.settings.*
import org.koin.dsl.*

fun appModule() = module {
    single { SettingsStore() }
    single { ExampleApi() }
    single { ExampleRepository(get()) }
}
