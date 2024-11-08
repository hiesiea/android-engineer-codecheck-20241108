/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentRepositoryDetailBinding

class RepositoryDetailFragment : Fragment(R.layout.fragment_repository_detail) {
    private val args: RepositoryDetailFragmentArgs by navArgs()

    private var binding: FragmentRepositoryDetailBinding? = null
    private val _binding get() = binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRepositoryDetailBinding.bind(view)
        _binding.ownerIconView.load(args.item.ownerIconUrl)
        _binding.nameView.text = args.item.name
        _binding.languageView.text = args.item.language
        _binding.starsView.text = getString(R.string.stars_count, args.item.stargazersCount)
        _binding.watchersView.text = getString(R.string.watchers_count, args.item.watchersCount)
        _binding.forksView.text = getString(R.string.forks_count, args.item.forksCount)
        _binding.openIssuesView.text = getString(R.string.open_issues_count, args.item.openIssuesCount)
    }
}
