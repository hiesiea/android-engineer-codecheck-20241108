package jp.co.yumemi.android.code_check.ui.search

import jp.co.yumemi.android.code_check.data.model.DataLoadingState
import jp.co.yumemi.android.code_check.data.model.RepositoryItem

data class RepositorySearchUiState(
    val dataLoadingState: DataLoadingState = DataLoadingState.Initial,
    val repositoryItems: List<RepositoryItem> = emptyList(),
)
