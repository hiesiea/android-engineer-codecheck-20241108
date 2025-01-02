package jp.co.yumemi.android.codecheck.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.codecheck.data.model.RepositoryItem
import jp.co.yumemi.android.codecheck.designsystem.MainTheme
import jp.co.yumemi.android.codecheck.search.RepositorySearchScreen
import jp.co.yumemi.android.codecheck.search.RepositorySearchViewModel

@AndroidEntryPoint
class RepositorySearchFragment : Fragment() {
    private val viewModel: RepositorySearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MainTheme {
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                    RepositorySearchScreen(
                        uiState = uiState,
                        onSearchButtonClick = { viewModel.searchRepositories(it) },
                        onItemClick = { navigateRepositoryDetail(it) },
                    )
                }
            }
        }
    }

    private fun navigateRepositoryDetail(item: RepositoryItem) {
        val action = RepositorySearchFragmentDirections
            .actionRepositorySearchFragmentToRepositoryDetailFragment(item = item)
        findNavController().navigate(action)
    }
}
