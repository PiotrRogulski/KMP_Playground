package features.endpoints

import features.endpoints.models.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.*

class ExampleApi(
    val client: HttpClient = HttpClient(CIO) {
        defaultRequest { url("https://reqres.in/api/") }
        install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
    },
) {
    suspend fun getUsers(page: Int = 1, perPage: Int = 4) = paginated<User>("users", page, perPage)
    suspend fun getUser(id: Int) = single<User>("users/$id")

    suspend fun getResources(page: Int = 1, perPage: Int = 4) = paginated<Resource>("unknown", page, perPage)
    suspend fun getResource(id: Int) = single<Resource>("unknown/$id")
}

private suspend inline fun <reified T> ExampleApi.single(query: String): SingleResponse<T> = client.get(query).body()

private suspend inline fun <reified T> ExampleApi.paginated(
    query: String,
    page: Int = 1,
    perPage: Int = 4,
): PaginatedResponse<T> = client.get(query) {
    parameter("page", page)
    parameter("per_page", perPage)
}.body()
