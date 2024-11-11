package jp.co.yumemi.android.code_check.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.model.RepositoryItem
import jp.co.yumemi.android.code_check.ui.theme.MainTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryDetailScreen(
    item: RepositoryItem,
    onCloseButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = item.name) },
                navigationIcon = {
                    IconButton(
                        onClick = onCloseButtonClick,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            tint = Color.Black,
                            contentDescription = null,
                        )
                    }
                },
            )
        },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item.ownerIconUrl?.let { ownerIconUrl ->
                if (LocalInspectionMode.current) {
                    // Preview 時
                    Image(
                        painter = painterResource(R.drawable.jetbrains),
                        contentDescription = "ownerIconUrl",
                    )
                } else {
                    // 通常時
                    AsyncImage(
                        model = ownerIconUrl,
                        contentDescription = "ownerIconUrl",
                    )
                }
            }
            item.language?.let { language ->
                Text(text = stringResource(R.string.written_language, language))
            }
            Text(text = stringResource(R.string.stars_count, item.stargazersCount))
            Text(text = stringResource(R.string.watchers_count, item.watchersCount))
            Text(text = stringResource(R.string.forks_count, item.forksCount))
            Text(text = stringResource(R.string.open_issues_count, item.openIssuesCount))
        }
    }
}

@Preview
@Composable
private fun RepositoryDetailScreenPreview() {
    MainTheme {
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
}
