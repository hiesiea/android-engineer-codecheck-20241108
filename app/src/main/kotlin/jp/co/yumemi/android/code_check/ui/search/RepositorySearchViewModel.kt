package jp.co.yumemi.android.code_check.ui.search

import androidx.lifecycle.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import jp.co.yumemi.android.code_check.data.model.RepositoryItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.json.JSONObject

/**
 * [RepositorySearchFragment] で使う
 */
class RepositorySearchViewModel : ViewModel() {

    /**
     * 与えられたキーワードをもとに検索処理を行う
     * @param inputText 検索するキーワード
     * @return 検索結果
     */
    fun searchResults(inputText: String): List<RepositoryItem> = runBlocking {
        val client = HttpClient(Android)

        return@runBlocking GlobalScope.async {
            val response: HttpResponse = client.get("https://api.github.com/search/repositories") {
                header("Accept", "application/vnd.github.v3+json")
                parameter("q", inputText)
            }
            val jsonBody = JSONObject(response.receive<String>())
            val jsonItems = jsonBody.optJSONArray("items") ?: return@async emptyList()
            val items = mutableListOf<RepositoryItem>()

            // アイテムの個数分ループし、items に格納する
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

            return@async items.toList()
        }.await()
    }
}
