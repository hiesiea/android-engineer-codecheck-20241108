package jp.co.yumemi.android.codecheck.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.codecheck.core.data.model.DataLoadingState
import jp.co.yumemi.android.codecheck.core.data.model.ErrorType
import jp.co.yumemi.android.codecheck.core.data.repository.SearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

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
            val repositoryItems = searchRepository.requestSearchRepositories(inputText = inputText)
            _uiState.value = RepositorySearchUiState(
                dataLoadingState = DataLoadingState.Success,
                repositoryItems = repositoryItems,
            )
        } catch (throwable: Throwable) {
            Timber.e(throwable)
            _uiState.value = RepositorySearchUiState(
                dataLoadingState = DataLoadingState.Failure(errorType = ErrorType.from(throwable = throwable)),
                repositoryItems = emptyList(),
            )
        }
    }
}
