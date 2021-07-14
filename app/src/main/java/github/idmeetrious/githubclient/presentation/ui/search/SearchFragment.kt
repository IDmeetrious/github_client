package github.idmeetrious.githubclient.presentation.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import github.idmeetrious.githubclient.databinding.FragmentSearchBinding
import kotlinx.coroutines.flow.collect

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    private var adapter: SearchAdapter? = null
    private var rv: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = SearchAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val rootView = binding.root
        rv = binding.searchRv

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv?.layoutManager = LinearLayoutManager(requireContext())
        rv?.adapter = adapter


        binding.searchBtn.setOnClickListener {
            val query = binding.searchEt.text?.trim()
            if (!query.isNullOrEmpty()) {
                viewModel.getReposByUser("$query")
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.repos.collect {
                adapter?.updateList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}