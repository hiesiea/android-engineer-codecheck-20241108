package jp.co.yumemi.android.codecheck.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.co.yumemi.android.codecheck.R
import jp.co.yumemi.android.codecheck.data.model.RepositoryItem
import jp.co.yumemi.android.codecheck.ui.common.OwnerIcon
import jp.co.yumemi.android.codecheck.ui.theme.MainTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryDetailScreen(
    item: RepositoryItem,
    onCloseButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    onShowDetailButtonClick: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = item.name,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onCloseButtonClick,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
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
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
                .verticalScroll(state = rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            OwnerIcon(
                ownerIconUrl = item.ownerIconUrl,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
            item.language?.let { language ->
                Text(
                    text = stringResource(R.string.written_language, language),
                    modifier = Modifier.testTag("language"),
                )
            }
            Text(text = stringResource(R.string.stars_count, item.stargazersCount))
            Text(text = stringResource(R.string.watchers_count, item.watchersCount))
            Text(text = stringResource(R.string.forks_count, item.forksCount))
            Text(text = stringResource(R.string.open_issues_count, item.openIssuesCount))
            Button(
                onClick = onShowDetailButtonClick,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                Text(text = stringResource(id = R.string.show_detail_button_label))
            }
        }
    }
}

@Preview
@Composable
private fun RepositoryDetailScreenPreview() {
    MainTheme {
        RepositoryDetailScreen(
            item = RepositoryItem.fake(),
            onCloseButtonClick = {},
            onShowDetailButtonClick = {},
        )
    }
}
