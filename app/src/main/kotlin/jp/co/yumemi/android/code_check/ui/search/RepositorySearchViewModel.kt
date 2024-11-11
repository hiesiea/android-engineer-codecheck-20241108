package jp.co.yumemi.android.code_check.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.data.model.RepositoryItem
import jp.co.yumemi.android.code_check.data.repository.SearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

/**
 * [RepositorySearchFragment] で使う
 */
@HiltViewModel
class RepositorySearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
) : ViewModel() {
    private val _repositoryItems = MutableStateFlow<List<RepositoryItem>>(emptyList())
    val repositoryItems: StateFlow<List<RepositoryItem>> = _repositoryItems.asStateFlow()

    /**
     * 与えられたキーワードをもとに検索処理を行う
     * @param inputText 検索するキーワード
     * @return 検索結果
     */
    fun searchRepositories(inputText: String) = viewModelScope.launch {
        try {
            val jsonStr = searchRepository.requestSearchRepositories(inputText = inputText)
            val jsonBody = JSONObject(jsonStr)
            val jsonItems = jsonBody.optJSONArray("items") ?: return@launch
            _repositoryItems.value = convertToRepositoryItems(jsonItems = jsonItems)
        } catch (throwable: Throwable) {
            Timber.e(throwable)
            _repositoryItems.value = emptyList()
        }
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
            val forksCount = jsonItem.optLong("forks_count")
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
