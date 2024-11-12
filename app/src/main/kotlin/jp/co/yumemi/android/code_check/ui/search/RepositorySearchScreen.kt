package jp.co.yumemi.android.code_check.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.model.RepositoryItem

@Composable
fun RepositorySearchScreen(
    repositoryItems: List<RepositoryItem>,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 12.dp),
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
        ) {
            items(repositoryItems) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp),
                ) {
                    val imageModifier = Modifier.size(80.dp)
                    if (LocalInspectionMode.current) {
                        // Preview 時
                        Image(
                            painter = painterResource(R.drawable.jetbrains),
                            contentDescription = "ownerIconUrl",
                            modifier = imageModifier,
                        )
                    } else {
                        // 通常時
                        AsyncImage(
                            model = item.ownerIconUrl,
                            contentDescription = "ownerIconUrl",
                            modifier = imageModifier,
                            error = painterResource(id = R.drawable.empty_image),
                        )
                    }
                    Column {
                        Text(
                            text = item.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2,
                        )
                        item.language?.let { language ->
                            Text(
                                text = stringResource(R.string.written_language, language),
                                modifier = Modifier
                                    .padding(vertical = 4.dp)
                                    .testTag("language"),
                            )
                        }
                        Text(text = stringResource(R.string.stars_count, item.stargazersCount))
                    }
                }
                HorizontalDivider()
            }
        }
    }
}

@Preview
@Composable
private fun RepositorySearchScreenPreview() {
    RepositorySearchScreen(
        repositoryItems = List(10) {
            RepositoryItem(
                name = "dtrupenn/Tetris",
                ownerIconUrl = "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",
                language = "Assembly",
                stargazersCount = 1,
                watchersCount = 1,
                forksCount = 0,
                openIssuesCount = 0,
            )
        },
    )
}
