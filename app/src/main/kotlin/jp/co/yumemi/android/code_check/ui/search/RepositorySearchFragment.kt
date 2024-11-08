package jp.co.yumemi.android.code_check.ui.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.model.RepositoryItem
import jp.co.yumemi.android.code_check.databinding.FragmentRepositorySearchBinding

class RepositorySearchFragment : Fragment(R.layout.fragment_repository_search) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = RepositorySearchViewModel(requireContext())
        val binding = FragmentRepositorySearchBinding.bind(view)
        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration =
            DividerItemDecoration(context, layoutManager.orientation)
        val adapter = RepositoryItemListAdapter(
            object : RepositoryItemListAdapter.OnItemClickListener {
                override fun itemClick(item: RepositoryItem) {
                    navigateRepositoryDetail(item)
                }
            },
        )
        binding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    editText.text.toString().let {
                        viewModel.searchResults(it).apply {
                            adapter.submitList(this)
                        }
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }
    }

    private fun navigateRepositoryDetail(item: RepositoryItem) {
        val action = RepositorySearchFragmentDirections
            .actionRepositorySearchFragmentToRepositoryDetailFragment(item = item)
        findNavController().navigate(action)
    }
}