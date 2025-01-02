package jp.co.yumemi.android.codecheck.detail

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import jp.co.yumemi.android.codecheck.core.data.model.RepositoryItem
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
    fun `language に何か設定されていれば表示されること`() {
        rule.setContent {
            RepositoryDetailScreen(
                item = RepositoryItem(language = "Assembly"),
                onCloseButtonClick = {},
                onShowDetailButtonClick = {},
            )
        }

        rule.onNodeWithTag("language").assertExists()
    }

    @Test
    fun `language に何も設定されていなければ、言語自体表示されないこと`() {
        rule.setContent {
            RepositoryDetailScreen(
                item = RepositoryItem(),
                onCloseButtonClick = {},
                onShowDetailButtonClick = {},
            )
        }

        rule.onNodeWithTag("language").assertDoesNotExist()
    }
}
