package jp.co.yumemi.android.code_check.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @see <a href="https://docs.github.com/en/rest/search/search?apiVersion=2022-11-28#search-repositories">Search repositories</a>
 */
@Serializable
data class SearchRepositoriesResponse(
    @SerialName("items") val items: List<SearchRepositoryResponse>,
)

@Serializable
data class SearchRepositoryResponse(
    @SerialName("full_name") val fullName: String,
    @SerialName("owner") val owner: Owner?,
    @SerialName("language") val language: String?,
    @SerialName("stargazers_count") val stargazersCount: Long,
    @SerialName("watchers_count") val watchersCount: Long,
    @SerialName("forks_count") val forksCount: Long,
    @SerialName("open_issues_count") val openIssuesCount: Long,
)

@Serializable
data class Owner(
    @SerialName("avatar_url") val avatarUrl: String,
)
