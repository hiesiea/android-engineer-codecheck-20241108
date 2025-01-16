package jp.co.yumemi.android.codecheck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.codecheck.core.data.model.RepositoryItem
import jp.co.yumemi.android.codecheck.core.designsystem.MainTheme
import jp.co.yumemi.android.codecheck.feature.search.RepositorySearchScreen

@AndroidEntryPoint
class RepositorySearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MainTheme {
                    RepositorySearchScreen(
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
