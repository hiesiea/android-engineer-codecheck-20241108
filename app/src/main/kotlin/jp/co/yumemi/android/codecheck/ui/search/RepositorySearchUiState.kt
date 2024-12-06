package jp.co.yumemi.android.codecheck.ui.search

import jp.co.yumemi.android.codecheck.data.model.DataLoadingState
import jp.co.yumemi.android.codecheck.data.model.RepositoryItem

data class RepositorySearchUiState(
    val dataLoadingState: DataLoadingState = DataLoadingState.Initial,
    val repositoryItems: List<RepositoryItem> = emptyList(),
)
