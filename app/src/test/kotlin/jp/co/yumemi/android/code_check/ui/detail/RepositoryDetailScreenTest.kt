package jp.co.yumemi.android.code_check.ui.detail

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import jp.co.yumemi.android.code_check.data.model.RepositoryItem
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.Test

@Suppress("NonAsciiCharacters")
@RunWith(RobolectricTestRunner::class)
class RepositoryDetailScreenTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun `ownerIconUrl や language に何か設定されていれば表示されること`() {
        rule.setContent {
            RepositoryDetailScreen(
                item = RepositoryItem(
                    name = "dtrupenn/Tetris",
                    ownerIconUrl = "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",
                    language = "Assembly",
                    stargazersCount = 1,
                    watchersCount = 1,
                    forksCount = 0,
                    openIssuesCount = 0,
                ),
                onCloseButtonClick = {},
            )
        }

        rule.onNodeWithContentDescription("ownerIconUrl").assertExists()
        rule.onNodeWithTag("language").assertExists()
    }

    @Test
    fun `language に何も設定されていなければ、言語自体表示されないこと`() {
        rule.setContent {
            RepositoryDetailScreen(
                item = RepositoryItem(
                    name = "dtrupenn/Tetris",
                    ownerIconUrl = "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",
                    language = null,
                    stargazersCount = 1,
                    watchersCount = 1,
                    forksCount = 0,
                    openIssuesCount = 0,
                ),
                onCloseButtonClick = {},
            )
        }

        rule.onNodeWithContentDescription("ownerIconUrl").assertExists()
        rule.onNodeWithTag("language").assertDoesNotExist()
    }
}
