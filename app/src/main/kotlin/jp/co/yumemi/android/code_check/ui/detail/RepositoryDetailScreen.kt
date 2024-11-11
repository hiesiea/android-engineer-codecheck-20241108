package jp.co.yumemi.android.code_check.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import jp.co.yumemi.android.code_check.data.model.RepositoryItem

@Composable
fun RepositoryDetailScreen(
    item: RepositoryItem,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {},
    ) {
        Column(modifier = Modifier.padding(it)) {

        }
    }
}

@Preview
@Composable
private fun RepositoryDetailScreenPreview() {
    RepositoryDetailScreen(
        item = RepositoryItem(
            name = "dtrupenn/Tetris",
            ownerIconUrl = "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",
            language = "Assembly",
            stargazersCount = 1,
            watchersCount = 1,
            forksCount = 0,
            openIssuesCount = 0,
        )
    )
}
