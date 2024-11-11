package jp.co.yumemi.android.code_check.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepositoryItem(
    val name: String,
    val ownerIconUrl: String,
    val language: String,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
) : Parcelable

fun SearchRepositoryResponse.toRepositoryItem(): RepositoryItem {
    return RepositoryItem(
        name = fullName,
        ownerIconUrl = owner?.avatarUrl ?: "",
        language = language ?: "",
        stargazersCount = stargazersCount,
        watchersCount = watchersCount,
        forksCount = forksCount,
        openIssuesCount = openIssuesCount,
    )
}
