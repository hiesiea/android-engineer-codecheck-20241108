package jp.co.yumemi.android.codecheck.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import jp.co.yumemi.android.codecheck.data.model.RepositoryItem
import jp.co.yumemi.android.codecheck.data.model.SearchRepositoriesResponse
import jp.co.yumemi.android.codecheck.data.model.toRepositoryItem
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val client: HttpClient,
) {
    suspend fun requestSearchRepositories(inputText: String): List<RepositoryItem> {
        val response: HttpResponse = client.get("https://api.github.com/search/repositories") {
            parameter("q", inputText)
        }
        val searchRepositoriesResponse = response.body<SearchRepositoriesResponse>()
        return searchRepositoriesResponse.items.map { it.toRepositoryItem() }
    }
}
