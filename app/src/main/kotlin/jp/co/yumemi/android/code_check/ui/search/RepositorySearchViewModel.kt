package jp.co.yumemi.android.code_check.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import jp.co.yumemi.android.code_check.data.model.RepositoryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

/**
 * [RepositorySearchFragment] で使う
 */
class RepositorySearchViewModel : ViewModel() {
    private val client = HttpClient(Android)
    private val _repositoryItems = MutableStateFlow<List<RepositoryItem>>(emptyList())
    val repositoryItems: StateFlow<List<RepositoryItem>> = _repositoryItems.asStateFlow()

    /**
     * 与えられたキーワードをもとに検索処理を行う
     * @param inputText 検索するキーワード
     * @return 検索結果
     */
    fun searchRepositories(inputText: String) = viewModelScope.launch {
        val jsonStr = requestSearchRepositories(inputText = inputText)
        val jsonBody = JSONObject(jsonStr)
        val jsonItems = jsonBody.optJSONArray("items") ?: return@launch
        _repositoryItems.value = convertToRepositoryItems(jsonItems = jsonItems)
    }

    private suspend fun requestSearchRepositories(inputText: String): String {
        val response: HttpResponse = client.get("https://api.github.com/search/repositories") {
            header("Accept", "application/vnd.github.v3+json")
            parameter("q", inputText)
        }
        return response.receive()
    }

    private fun convertToRepositoryItems(jsonItems: JSONArray): List<RepositoryItem> {
        val items = mutableListOf<RepositoryItem>()
        for (i in 0 until jsonItems.length()) {
            val jsonItem = jsonItems.optJSONObject(i) ?: continue
            val name = jsonItem.optString("full_name")
            val ownerIconUrl = jsonItem.optJSONObject("owner")?.optString("avatar_url") ?: ""
            val language = jsonItem.optString("language")
            val stargazersCount = jsonItem.optLong("stargazers_count")
            val watchersCount = jsonItem.optLong("watchers_count")
            val forksCount = jsonItem.optLong("forks_conut")
            val openIssuesCount = jsonItem.optLong("open_issues_count")

            items.add(
                RepositoryItem(
                    name = name,
                    ownerIconUrl = ownerIconUrl,
                    language = language,
                    stargazersCount = stargazersCount,
                    watchersCount = watchersCount,
                    forksCount = forksCount,
                    openIssuesCount = openIssuesCount,
                ),
            )
        }
        return items.toList()
    }
}
