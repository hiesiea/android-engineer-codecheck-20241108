package jp.co.yumemi.android.code_check.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val client: HttpClient,
) {
    suspend fun requestSearchRepositories(inputText: String): String {
        val response: HttpResponse = client.get("https://api.github.com/search/repositories") {
            header("Accept", "application/vnd.github.v3+json")
            parameter("q", inputText)
        }
        return response.receive()
    }
}
