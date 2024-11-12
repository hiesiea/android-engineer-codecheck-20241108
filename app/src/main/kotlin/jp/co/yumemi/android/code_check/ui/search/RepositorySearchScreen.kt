package jp.co.yumemi.android.code_check.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.model.RepositoryItem
import jp.co.yumemi.android.code_check.ui.common.OwnerIcon

@Composable
fun RepositorySearchScreen(
    repositoryItems: List<RepositoryItem>,
    modifier: Modifier = Modifier,
    onSearchButtonClick: (String) -> Unit,
    onItemClick: (RepositoryItem) -> Unit,
) {
    var text by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier,
        topBar = {
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("SearchTextField"),
                placeholder = { Text(text = stringResource(id = R.string.search_input_text_hint)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 8.dp),
                    )
                },
                trailingIcon = {
                    if (text.isNotEmpty()) {
                        IconButton(
                            onClick = { text = "" },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "ClearIconButton",
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = { onSearchButtonClick(text) },
                ),
                maxLines = 1,
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
        ) {
            items(repositoryItems) { item ->
                Row(
                    modifier = Modifier
                        .clickable { onItemClick(item) }
                        .fillMaxWidth()
                        .padding(all = 8.dp),
                ) {
                    OwnerIcon(
                        ownerIconUrl = item.ownerIconUrl,
                        modifier = Modifier.size(80.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
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
        onSearchButtonClick = {},
        onItemClick = {},
    )
}
