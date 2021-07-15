package github.idmeetrious.githubclient.presentation.ui.downloaded

import androidx.lifecycle.ViewModel
import github.idmeetrious.githubclient.domain.common.State
import github.idmeetrious.githubclient.domain.entities.GitRepo
import github.idmeetrious.githubclient.domain.usecase.GetSavedRepositoriesUseCase
import github.idmeetrious.githubclient.presentation.application.App
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "DownloadedViewModel"

class DownloadedViewModel : ViewModel() {
    private val ioScope = CoroutineScope(Dispatchers.IO + Job())
    private var job: Job? = null

    @Inject
    lateinit var getSavedRepositoryUseCase: GetSavedRepositoriesUseCase

    private var disposable: Disposable? = null

    private var _repos: MutableStateFlow<List<GitRepo>> = MutableStateFlow(emptyList())
    val repos get() = _repos

    private var _loadState: MutableStateFlow<State> = MutableStateFlow(State.EMPTY)
    val loadState get() = _loadState

    init {
        App.appComponent.inject(this)
        loadReposFromDb()
    }

    fun loadReposFromDb() {
        job = ioScope.launch {
            _loadState.value = State.LOADING
            disposable = getSavedRepositoryUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .doOnSuccess {
                    _loadState.value = State.SUCCESS
                }
                .subscribe({
                    _repos.value = it
                }, {
                    _loadState.value = State.ERROR
                })
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
        job?.cancel()
    }
}