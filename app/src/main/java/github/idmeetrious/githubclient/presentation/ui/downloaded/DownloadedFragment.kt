package github.idmeetrious.githubclient.presentation.ui.downloaded

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import github.idmeetrious.githubclient.databinding.FragmentDownloadedBinding
import github.idmeetrious.githubclient.domain.common.State
import kotlinx.coroutines.flow.collect

private const val TAG = "DownloadedFragment"

class DownloadedFragment : Fragment() {
    private var _binding: FragmentDownloadedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DownloadedViewModel by lazy {
        ViewModelProvider(this).get(DownloadedViewModel::class.java)
    }

    private var adapter: DownloadedAdapter? = null
    private var rv: RecyclerView? = null
    private var pb: ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDownloadedBinding.inflate(inflater, container, false)
        val rootView = binding.root
        rv = binding.downloadedRv
        pb = binding.downloadPb
        adapter = DownloadedAdapter()

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv?.layoutManager = LinearLayoutManager(requireContext())
        rv?.adapter = adapter
        rv?.setHasFixedSize(true)

        observeAdapterData()
        observeLoadProgress()
    }

    private fun observeLoadProgress() {

        lifecycleScope.launchWhenStarted {
            viewModel.loadState.collect { state ->
                when (state) {
                    State.LOADING -> {
                        pb?.visibility = View.VISIBLE
                        rv?.visibility = View.INVISIBLE
                    }
                    State.SUCCESS -> {
                        pb?.visibility = View.INVISIBLE
                        rv?.visibility = View.VISIBLE
                    }
                    State.ERROR -> {
                        view?.let {
                            Snackbar.make(
                                it, "Can't load information..",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                        pb?.visibility = View.GONE
                        rv?.visibility = View.GONE
                    }
                    State.EMPTY -> {
                        pb?.visibility = View.INVISIBLE
                        rv?.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "--> onResume: ")
        viewModel.loadReposFromDb()

    }

    private fun observeAdapterData() {
        lifecycleScope.launchWhenStarted {
            viewModel.repos.collect {
                adapter?.updateList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter = null
    }
}