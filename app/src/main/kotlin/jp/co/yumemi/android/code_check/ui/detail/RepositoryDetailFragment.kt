/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentRepositoryDetailBinding

class RepositoryDetailFragment : Fragment(R.layout.fragment_repository_detail) {
    private val args: RepositoryDetailFragmentArgs by navArgs()

    private var _binding: FragmentRepositoryDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ownerIconView.load(args.item.ownerIconUrl)
        binding.nameView.text = args.item.name
        binding.languageView.text = getString(R.string.written_language, args.item.language)
        binding.starsView.text = getString(R.string.stars_count, args.item.stargazersCount)
        binding.watchersView.text = getString(R.string.watchers_count, args.item.watchersCount)
        binding.forksView.text = getString(R.string.forks_count, args.item.forksCount)
        binding.openIssuesView.text =
            getString(R.string.open_issues_count, args.item.openIssuesCount)
    }
}
