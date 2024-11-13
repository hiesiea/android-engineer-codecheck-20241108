package jp.co.yumemi.android.code_check.ui.search

import jp.co.yumemi.android.code_check.data.model.DataLoadingState
import jp.co.yumemi.android.code_check.data.model.RepositoryItem

data class RepositorySearchUiState(
    val dataLoadingState: DataLoadingState = DataLoadingState.InProgress,
    val repositoryItems: List<RepositoryItem> = emptyList(),
)
