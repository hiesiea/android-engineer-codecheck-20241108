package jp.co.yumemi.android.code_check.ui.search

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
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
}
