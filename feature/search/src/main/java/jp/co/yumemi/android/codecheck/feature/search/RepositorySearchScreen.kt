package jp.co.yumemi.android.codecheck.feature.search

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.co.yumemi.android.codecheck.core.data.model.DataLoadingState
import jp.co.yumemi.android.codecheck.core.data.model.ErrorType
import jp.co.yumemi.android.codecheck.core.data.model.RepositoryItem
import jp.co.yumemi.android.codecheck.core.designsystem.MainTheme
import jp.co.yumemi.android.codecheck.core.ui.OwnerIcon
import kotlinx.coroutines.coroutineScope

@Composable
fun RepositorySearchScreen(
    viewModel: RepositorySearchViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onItemClick: (RepositoryItem) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    RepositorySearchScreen(
        uiState = uiState,
        modifier = modifier,
        onSearchButtonClick = { viewModel.searchRepositories(it) },
        onItemClick = onItemClick,
    )
}

@VisibleForTesting
@Composable
fun RepositorySearchScreen(
    uiState: RepositorySearchUiState,
    modifier: Modifier = Modifier,
    onSearchButtonClick: (String) -> Unit,
    onItemClick: (RepositoryItem) -> Unit,
) {
    Scaffold(
        modifier = modifier.systemBarsPadding(),
        topBar = {
            SearchTextField(onSearchButtonClick = onSearchButtonClick)
        },
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (uiState.dataLoadingState) {
                is DataLoadingState.Initial -> {
                    InitialView()
                }

                is DataLoadingState.InProgress -> {
                    InProgressView()
                }

                is DataLoadingState.Success -> {
                    SuccessView(
                        repositoryItems = uiState.repositoryItems,
                        onItemClick = onItemClick,
                    )
                }

                is DataLoadingState.Failure -> {
                    FailureView(errorType = uiState.dataLoadingState.errorType)
                }
            }
        }
    }
}

@Composable
private fun SearchTextField(
    modifier: Modifier = Modifier,
    onSearchButtonClick: (String) -> Unit,
) {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        modifier = modifier
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
}

@Composable
private fun InitialView(modifier: Modifier = Modifier) {
    Box(modifier = modifier.testTag("InitialView"))
}

@Composable
private fun InProgressView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("InProgressView"),
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.Center),
        )
    }
}

@Composable
private fun SuccessView(
    repositoryItems: List<RepositoryItem>,
    modifier: Modifier = Modifier,
    onItemClick: (RepositoryItem) -> Unit,
) {
    LazyColumn(
        modifier = modifier.disableSplitMotionEvents(),
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

@Composable
private fun FailureView(
    modifier: Modifier = Modifier,
    errorType: ErrorType,
) {
    val stringResId = when (errorType) {
        // 例外の種類によってメッセージを切り替える
        ErrorType.NETWORK_ERROR -> R.string.network_error_text
        else -> R.string.other_error_text
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = stringResource(id = stringResId))
    }
}

/**
 * 複数の項目が同時にタップされた場合に、複数の処理が走らないようにする。
 * @see <a href="https://stackoverflow.com/a/72816456/10867055">android - How to disable simultaneous clicks on multiple items in Jetpack Compose List / Column / Row (out of the box debounce?) - Stack Overflow</a>
 */
private fun Modifier.disableSplitMotionEvents() = then(
    pointerInput(Unit) {
        coroutineScope {
            var currentId: Long = -1L
            awaitPointerEventScope {
                while (true) {
                    awaitPointerEvent(PointerEventPass.Initial).changes.forEach { pointerInfo ->
                        when {
                            pointerInfo.pressed && currentId == -1L -> currentId = pointerInfo.id.value
                            pointerInfo.pressed.not() && currentId == pointerInfo.id.value -> currentId = -1
                            pointerInfo.id.value != currentId && currentId != -1L -> pointerInfo.consume()
                            else -> Unit
                        }
                    }
                }
            }
        }
    },
)

private class DataLoadingStateProvider : PreviewParameterProvider<DataLoadingState> {
    override val values: Sequence<DataLoadingState>
        get() = sequenceOf(
            DataLoadingState.Initial,
            DataLoadingState.InProgress,
            DataLoadingState.Success,
            DataLoadingState.Failure(errorType = ErrorType.NETWORK_ERROR),
            DataLoadingState.Failure(errorType = ErrorType.OTHER_ERROR),
        )
}

@Preview
@Composable
private fun RepositorySearchScreenPreview(
    @PreviewParameter(DataLoadingStateProvider::class) dataLoadingState: DataLoadingState,
) {
    MainTheme {
        RepositorySearchScreen(
            uiState = RepositorySearchUiState(
                dataLoadingState = dataLoadingState,
                repositoryItems = List(10) {
                    RepositoryItem.fake()
                },
            ),
            onSearchButtonClick = {},
            onItemClick = {},
        )
    }
}
