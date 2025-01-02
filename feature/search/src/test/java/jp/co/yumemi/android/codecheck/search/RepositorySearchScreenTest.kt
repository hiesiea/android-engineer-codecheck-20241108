package jp.co.yumemi.android.codecheck.search

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import jp.co.yumemi.android.codecheck.data.model.DataLoadingState
import jp.co.yumemi.android.codecheck.data.model.ErrorType
import jp.co.yumemi.android.codecheck.data.model.RepositoryItem
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.Test

@Suppress("NonAsciiCharacters", "TestFunctionName")
@RunWith(RobolectricTestRunner::class)
class RepositorySearchScreenTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun 入力フォームの挙動に問題ないこと() {
        rule.setContent {
            RepositorySearchScreen(
                uiState = RepositorySearchUiState(),
                onSearchButtonClick = {},
                onItemClick = {},
            )
        }

        val searchTextField = rule.onNodeWithTag("SearchTextField")
        val clearIconButton = rule.onNodeWithContentDescription("ClearIconButton")

        // 何も入力しなければクリアボタンを表示しない
        searchTextField.performTextInput("")
        clearIconButton.assertDoesNotExist()

        // 何か入力されたらクリアボタンを表示する
        searchTextField.performTextInput("test")
        clearIconButton.assertIsDisplayed()

        // クリアボタンをタップしたら入力フォームがクリアされてクリアボタンが非表示になること
        clearIconButton.performClick()
        clearIconButton.assertDoesNotExist()
        searchTextField.assert(hasText(""))
    }

    @Test
    fun 初回表示時はInitialViewが表示されること() {
        rule.setContent {
            RepositorySearchScreen(
                uiState = RepositorySearchUiState(
                    dataLoadingState = DataLoadingState.Initial,
                ),
                onSearchButtonClick = {},
                onItemClick = {},
            )
        }

        rule.onNodeWithTag("InitialView").assertExists()
    }

    @Test
    fun データ読み込み時はInProgressViewが表示されること() {
        rule.setContent {
            RepositorySearchScreen(
                uiState = RepositorySearchUiState(
                    dataLoadingState = DataLoadingState.InProgress,
                ),
                onSearchButtonClick = {},
                onItemClick = {},
            )
        }

        rule.onNodeWithTag("InProgressView").assertExists()
    }

    @Test
    fun データ読み込み成功時はSuccessViewが表示されること() {
        rule.setContent {
            RepositorySearchScreen(
                uiState = RepositorySearchUiState(
                    dataLoadingState = DataLoadingState.Success,
                    repositoryItems = listOf(
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
                    ),
                ),
                onSearchButtonClick = {},
                onItemClick = {},
            )
        }

        rule.onNodeWithContentDescription("OwnerIcon").assertIsDisplayed()
        rule.onNodeWithText("dtrupenn/Tetris").assertIsDisplayed()
        rule.onNodeWithText("Written in Assembly").assertIsDisplayed()
        rule.onNodeWithText("1 stars").assertIsDisplayed()
    }

    @Test
    fun `データ読み込み成功時にlanguageが設定されていなければ、言語自体表示されないこと`() {
        rule.setContent {
            RepositorySearchScreen(
                uiState = RepositorySearchUiState(
                    dataLoadingState = DataLoadingState.Success,
                    repositoryItems = listOf(
                        RepositoryItem(
                            name = "dtrupenn/Tetris",
                            ownerIconUrl = "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",
                            htmlUrl = "https://github.com/dtrupenn/Tetris",
                            stargazersCount = 1,
                            watchersCount = 1,
                            forksCount = 0,
                            openIssuesCount = 0,
                        ),
                    ),
                ),
                onSearchButtonClick = {},
                onItemClick = {},
            )
        }

        rule.onNodeWithContentDescription("OwnerIcon").assertIsDisplayed()
        rule.onNodeWithText("dtrupenn/Tetris").assertIsDisplayed()
        rule.onNodeWithTag("language").assertDoesNotExist()
        rule.onNodeWithText("1 stars").assertIsDisplayed()
    }

    @Test
    fun `データ読み込み失敗時に NETWORK_ERROR だったら、ネットワークエラーの旨が表示されること`() {
        rule.setContent {
            RepositorySearchScreen(
                uiState = RepositorySearchUiState(
                    dataLoadingState = DataLoadingState.Failure(errorType = ErrorType.NETWORK_ERROR),
                ),
                onSearchButtonClick = {},
                onItemClick = {},
            )
        }

        rule.onNodeWithText("検索に失敗しました。ネットワークに接続されているか確認し、しばらく待ってから再度お試しください。").assertIsDisplayed()
    }

    @Test
    fun `データ読み込み失敗時に OTHER_ERROR だったら、障害が発生している旨が表示されること`() {
        rule.setContent {
            RepositorySearchScreen(
                uiState = RepositorySearchUiState(
                    dataLoadingState = DataLoadingState.Failure(errorType = ErrorType.OTHER_ERROR),
                ),
                onSearchButtonClick = {},
                onItemClick = {},
            )
        }

        rule.onNodeWithText("検索に失敗しました。障害が発生している可能性があるため、開発者にお問い合わせください。").assertIsDisplayed()
    }
}
