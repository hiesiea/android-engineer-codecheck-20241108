package jp.co.yumemi.android.codecheck.search

import app.cash.turbine.test
import jp.co.yumemi.android.codecheck.core.data.model.DataLoadingState
import jp.co.yumemi.android.codecheck.core.data.model.ErrorType
import jp.co.yumemi.android.codecheck.core.data.model.RepositoryItem
import jp.co.yumemi.android.codecheck.core.data.repository.SearchRepository
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress("NonAsciiCharacters")
@RunWith(RobolectricTestRunner::class)
class RepositorySearchViewModelTest {
    private val searchRepository: SearchRepository = mock()
    private val viewModel = RepositorySearchViewModel(searchRepository = searchRepository)

    @Test
    fun `APIから正常なデータが返ってきた場合、uiState にそのデータが返ってくること`() = runTest {
        val inputText = "テスト"
        val repositoryItems = listOf(
            RepositoryItem(),
        )
        whenever(searchRepository.requestSearchRepositories(inputText)).thenReturn(repositoryItems)

        viewModel.searchRepositories(inputText = inputText)

        viewModel.uiState.test {
            val expected = RepositorySearchUiState(
                dataLoadingState = DataLoadingState.Success,
                repositoryItems = repositoryItems,
            )
            assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun `APIからエラーが返ってきた場合、uiState にエラーと空のデータが設定されること`() = runTest {
        val exception = IllegalStateException("テスト")
        whenever(searchRepository.requestSearchRepositories(any())).thenThrow(exception)

        viewModel.searchRepositories(inputText = "テスト")

        viewModel.uiState.test {
            val expected = RepositorySearchUiState(
                dataLoadingState = DataLoadingState.Failure(errorType = ErrorType.from(throwable = exception)),
                repositoryItems = emptyList(),
            )
            assertEquals(expected, awaitItem())
        }
    }
}
