/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.detail

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import jp.co.yumemi.android.code_check.ui.theme.MainTheme

class RepositoryDetailFragment : Fragment() {
    private val args: RepositoryDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MainTheme {
                    RepositoryDetailScreen(
                        item = args.item,
                        onCloseButtonClick = {
                            if (!findNavController().navigateUp()) {
                                activity?.finish()
                            }
                        },
                        onShowDetailButtonClick = {
                            val activity = this@RepositoryDetailFragment.activity ?: return@RepositoryDetailScreen
                            val intent = CustomTabsIntent.Builder().build()
                            intent.launchUrl(activity, Uri.parse(args.item.url))
                        },
                    )
                }
            }
        }
    }
}
