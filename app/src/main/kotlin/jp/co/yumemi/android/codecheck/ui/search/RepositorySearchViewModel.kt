package jp.co.yumemi.android.codecheck.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.codecheck.data.model.DataLoadingState
import jp.co.yumemi.android.codecheck.data.model.toRepositoryItem
import jp.co.yumemi.android.codecheck.data.repository.SearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * [RepositorySearchFragment] で使う
 */
@HiltViewModel
class RepositorySearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RepositorySearchUiState())
    val uiState: StateFlow<RepositorySearchUiState> = _uiState.asStateFlow()

    /**
     * 与えられたキーワードをもとに検索処理を行う
     * @param inputText 検索するキーワード
     * @return 検索結果
     */
    fun searchRepositories(inputText: String) = viewModelScope.launch {
        try {
            _uiState.value = RepositorySearchUiState(dataLoadingState = DataLoadingState.InProgress)
            val response = searchRepository.requestSearchRepositories(inputText = inputText)
            _uiState.value = RepositorySearchUiState(
                dataLoadingState = DataLoadingState.Success,
                repositoryItems = response.items.map { it.toRepositoryItem() },
            )
        } catch (throwable: Throwable) {
            Timber.e(throwable)
            _uiState.value = RepositorySearchUiState(
                dataLoadingState = DataLoadingState.Failure(throwable = throwable),
                repositoryItems = emptyList(),
            )
        }
    }
}
