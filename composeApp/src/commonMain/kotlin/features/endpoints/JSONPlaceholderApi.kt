package features.endpoints

import features.endpoints.json_placeholder.models.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*

class JSONPlaceholderApi {
    private val client = HttpClient(CIO) {
        defaultRequest {
            url("https://jsonplaceholder.typicode.com/")
        }

        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getUsers(): Array<User> = client.get("users").body()

}
