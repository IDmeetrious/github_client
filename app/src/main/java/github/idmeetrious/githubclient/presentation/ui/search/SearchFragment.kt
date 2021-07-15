package github.idmeetrious.githubclient.presentation.ui.search

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import github.idmeetrious.githubclient.databinding.FragmentSearchBinding
import github.idmeetrious.githubclient.domain.common.State
import github.idmeetrious.githubclient.domain.entities.GitRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

private const val TAG = "SearchFragment"
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    private var adapter: SearchAdapter? = null
    private var rv: RecyclerView? = null
    private var pb: ProgressBar? = null
    private var saveRepo: GitRepo? = null
    private var openRepo: GitRepo? = null
    private val WRITE_PERMISSION_CODE = 1000

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
        pb = binding.searchPb

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
                hideKeyboard()
            }
        }

        observeAdapterData()
        observeSaveRepo()
        observeOpenRepo()
        observeApiProgress()
    }

    private fun observeOpenRepo() {
        lifecycleScope.launchWhenStarted {
            adapter?.openRepo?.collect { repo ->
                repo?.let {
                    openRepo = it
                    openWeb(it.url)
                }
            }
        }
    }

    private fun openWeb(uri: String) {
        if (uri.isNotEmpty()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            intent.putExtra("repo_url", uri)
            startActivity(intent)
        }
    }

    private fun observeApiProgress() {
        lifecycleScope.launchWhenStarted {
            viewModel.apiState.collect { state ->
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
                            Snackbar.make(it, "Can't load information, try again!",
                                Snackbar.LENGTH_SHORT).show()
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
            binding.searchPb
        }
    }

    private fun observeSaveRepo() {
        lifecycleScope.launchWhenStarted {
            adapter?.saveRepo?.collect { repo ->
                repo?.let {
                    saveRepo = it
                    checkPermissionAndStartDownloading()
                }
            }
        }
    }

    private fun observeAdapterData() {
        lifecycleScope.launchWhenStarted {
            viewModel.repos.collect {
                adapter?.updateList(it)
            }
        }
    }

    private fun hideKeyboard() {
        val inputManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        inputManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun checkPermissionAndStartDownloading() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requireActivity().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED
            ) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    WRITE_PERMISSION_CODE
                )
            } else {
                downloadZip()
            }
        } else {
            downloadZip()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            WRITE_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    downloadZip()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Permission is not granted",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

    private fun downloadZip() {
        saveRepo?.let {
            val path = "${it.url}/archive/master.zip"
            val request = DownloadManager.Request(Uri.parse(path))
                .apply {
                    setTitle("${it.owner.login}.zip")
                    setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS,
                        "${it.owner.login}.zip"
                    )
                }
            val manager =
                requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

            lifecycleScope.launchWhenStarted {
                manager.enqueue(request)
            }.invokeOnCompletion { e ->
                if(e == null){
                    Log.i(TAG, "--> downloadZip: Completed")
                    viewModel.saveToDb(it)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}