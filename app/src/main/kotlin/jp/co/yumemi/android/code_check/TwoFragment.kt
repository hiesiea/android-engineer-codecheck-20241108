/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.databinding.FragmentTwoBinding

class TwoFragment : Fragment(R.layout.fragment_two) {

    private val args: TwoFragmentArgs by navArgs()

    private var binding: FragmentTwoBinding? = null
    private val _binding get() = binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("検索した日時", lastSearchDate.toString())

        binding = FragmentTwoBinding.bind(view)

        _binding.ownerIconView.load(args.item.ownerIconUrl)
        _binding.nameView.text = args.item.name
        _binding.languageView.text = args.item.language
        _binding.starsView.text = "${args.item.stargazersCount} stars"
        _binding.watchersView.text = "${args.item.watchersCount} watchers"
        _binding.forksView.text = "${args.item.forksCount} forks"
        _binding.openIssuesView.text = "${args.item.openIssuesCount} open issues"
    }
}
