package jp.co.yumemi.android.codecheck.feature.search

import jp.co.yumemi.android.codecheck.core.data.model.DataLoadingState
import jp.co.yumemi.android.codecheck.core.data.model.RepositoryItem

data class RepositorySearchUiState(
    val dataLoadingState: DataLoadingState = DataLoadingState.Initial,
    val repositoryItems: List<RepositoryItem> = emptyList(),
)
