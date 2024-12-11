package jp.co.yumemi.android.codecheck.ui.search

import app.cash.turbine.test
import jp.co.yumemi.android.codecheck.data.model.DataLoadingState
import jp.co.yumemi.android.codecheck.data.model.ErrorType
import jp.co.yumemi.android.codecheck.data.model.RepositoryItem
import jp.co.yumemi.android.codecheck.data.repository.SearchRepository
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
            RepositoryItem(
                name = "dtrupenn/Tetris",
                ownerIconUrl = "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",
                htmlUrl = "https://github.com/dtrupenn/Tetris",
                language = "Assembly",
                stargazersCount = 1,
                watchersCount = 1,
                forksCount = 0,
                openIssuesCount = 0,
            ),
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
