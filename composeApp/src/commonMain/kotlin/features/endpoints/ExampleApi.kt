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

class ExampleApi {
    private val client = HttpClient(CIO) {
        defaultRequest {
            url("https://reqres.in/api/")
        }

        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun getUsers(
        page: Int = 1,
        perPage: Int = 4,
    ): PaginatedResponse<User> = client.get("users") {
        parameter("page", page)
        parameter("per_page", perPage)
    }.body()
}
