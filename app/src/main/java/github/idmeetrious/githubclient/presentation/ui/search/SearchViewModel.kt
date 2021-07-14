package github.idmeetrious.githubclient.presentation.ui.search

import android.os.Environment
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import github.idmeetrious.githubclient.domain.common.State
import github.idmeetrious.githubclient.domain.entities.GitRepo
import github.idmeetrious.githubclient.domain.usecase.DownloadRepositoryUseCase
import github.idmeetrious.githubclient.domain.usecase.GetUserRepositoriesUseCase
import github.idmeetrious.githubclient.presentation.application.App
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.*
import javax.inject.Inject

private const val TAG = "SearchViewModel"
class SearchViewModel : ViewModel() {
    @Inject
    lateinit var getRepositoriesUseCase: GetUserRepositoriesUseCase

    @Inject
    lateinit var downloadRepositoryUseCase: DownloadRepositoryUseCase


    private var disposable: Disposable? = null
    private var _apiState: MutableStateFlow<State> = MutableStateFlow(State.EMPTY)
    val apiState get() = _apiState

    private var _downloadState: MutableStateFlow<State> = MutableStateFlow(State.EMPTY)
    val downloadState get() = _downloadState

    private var _repos: MutableStateFlow<List<GitRepo>> = MutableStateFlow(emptyList())
    val repos get() = _repos

    init {
        App.appComponent.inject(this)
    }

    fun getReposByUser(name: String) {
        viewModelScope.launch {
            _apiState.value = State.LOADING
        }
        disposable = getRepositoriesUseCase.invoke(name)
            .doOnSuccess {
                viewModelScope.launch {
                    _apiState.value = State.SUCCESS
                }
            }
            .subscribe({
                viewModelScope.launch {
                    _repos.value = it
                }
            }, {
                viewModelScope.launch {
                    _apiState.value = State.ERROR
                }
            })
    }

    fun downloadRepo(uri: String) {
        viewModelScope.launch {
            _downloadState.value = State.LOADING
        }
        disposable = downloadRepositoryUseCase.invoke(uri)
            .doOnSuccess {
                viewModelScope.launch {
                    _downloadState.value = State.SUCCESS
                }
            }
            .subscribe({
                viewModelScope.launch {
                    checkWritePermission()
                    saveToFiles(it)
                }
            }, {
                viewModelScope.launch {
                    _downloadState.value = State.ERROR
                }
            })
    }

    private fun checkWritePermission() {

    }

    private fun saveToFiles(data: ByteArray?) {
        val storage: File? = App().applicationContext
            .getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        val output = FileOutputStream("$storage/")
        try {
            data?.let {
                Log.i(TAG, "saveToFiles: Saving.. ${it.size / 1024}KB")
                output.write(it)
            }

        } catch (e: IOException) {
            Log.e(TAG, "saveToFiles: ${e.message}")
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}