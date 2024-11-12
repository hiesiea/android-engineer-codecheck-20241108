package jp.co.yumemi.android.code_check.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepositoryItem(
    val name: String,
    val ownerIconUrl: String,
    val htmlUrl: String,
    val language: String?,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
) : Parcelable {
    companion object {
        fun fake(): RepositoryItem {
            return RepositoryItem(
                name = "dtrupenn/Tetris",
                ownerIconUrl = "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",
                htmlUrl = "https://api.github.com/repos/dtrupenn/Tetris",
                language = "Assembly",
                stargazersCount = 1,
                watchersCount = 1,
                forksCount = 0,
                openIssuesCount = 0,
            )
        }
    }
}

fun SearchRepositoryResponse.toRepositoryItem(): RepositoryItem {
    return RepositoryItem(
        name = fullName,
        ownerIconUrl = owner?.avatarUrl ?: "",
        htmlUrl = htmlUrl,
        language = language,
        stargazersCount = stargazersCount,
        watchersCount = watchersCount,
        forksCount = forksCount,
        openIssuesCount = openIssuesCount,
    )
}
