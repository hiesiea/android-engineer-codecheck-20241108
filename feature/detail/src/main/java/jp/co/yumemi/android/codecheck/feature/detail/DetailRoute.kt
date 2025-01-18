package jp.co.yumemi.android.codecheck.feature.detail

import kotlinx.serialization.Serializable

@Serializable
data class DetailRoute(
    val name: String,
    val ownerIconUrl: String,
    val htmlUrl: String,
    val language: String?,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
)
