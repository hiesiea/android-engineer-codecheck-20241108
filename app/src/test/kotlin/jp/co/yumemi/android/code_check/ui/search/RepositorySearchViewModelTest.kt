package jp.co.yumemi.android.code_check.ui.search

import app.cash.turbine.test
import jp.co.yumemi.android.code_check.data.model.Owner
import jp.co.yumemi.android.code_check.data.model.RepositoryItem
import jp.co.yumemi.android.code_check.data.model.SearchRepositoriesResponse
import jp.co.yumemi.android.code_check.data.model.SearchRepositoryResponse
import jp.co.yumemi.android.code_check.data.repository.SearchRepository
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
    fun `APIから正常なデータが返ってきた場合、repositoryItems にそのデータが返ってくること`() = runTest {
        val searchRepositoriesResponse = SearchRepositoriesResponse(
            items = listOf(
                SearchRepositoryResponse(
                    fullName = "dtrupenn/Tetris",
                    owner = Owner(avatarUrl = "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png"),
                    language = "Assembly",
                    stargazersCount = 1,
                    watchersCount = 1,
                    forksCount = 0,
                    openIssuesCount = 0,
                ),
            ),
        )
        whenever(searchRepository.requestSearchRepositories(any())).thenReturn(searchRepositoriesResponse)

        viewModel.searchRepositories(inputText = "テスト")

        viewModel.repositoryItems.test {
            val expected = listOf(
                RepositoryItem(
                    name = "dtrupenn/Tetris",
                    ownerIconUrl = "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",
                    language = "Assembly",
                    stargazersCount = 1,
                    watchersCount = 1,
                    forksCount = 0,
                    openIssuesCount = 0,
                ),
            )
            assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun `APIからエラーが返ってきた場合、repositoryItems に空のデータが設定されること`() = runTest {
        whenever(searchRepository.requestSearchRepositories(any())).thenThrow(IllegalStateException("テスト"))

        viewModel.searchRepositories(inputText = "テスト")

        viewModel.repositoryItems.test {
            val expected = emptyList<RepositoryItem>()
            assertEquals(expected, awaitItem())
        }
    }
}
