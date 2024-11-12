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
    @SerialName("html_url") val htmlUrl: String,
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

fun SearchRepositoryResponse.Companion.fake(): SearchRepositoryResponse {
    return SearchRepositoryResponse(
        fullName = "dtrupenn/Tetris",
        owner = Owner(avatarUrl = "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png"),
        htmlUrl = "https://github.com/dtrupenn/Tetris",
        language = "Assembly",
        stargazersCount = 1,
        watchersCount = 1,
        forksCount = 0,
        openIssuesCount = 0,
    )
}
