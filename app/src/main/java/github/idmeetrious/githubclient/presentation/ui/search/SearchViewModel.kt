package github.idmeetrious.githubclient.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import github.idmeetrious.githubclient.domain.common.State
import github.idmeetrious.githubclient.domain.entities.GitRepo
import github.idmeetrious.githubclient.domain.usecase.GetUserRepositoriesUseCase
import github.idmeetrious.githubclient.domain.usecase.SaveRepositoryToDbUseCase
import github.idmeetrious.githubclient.presentation.application.App
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SearchViewModel"

class SearchViewModel : ViewModel() {
    @Inject
    lateinit var getRepositoriesUseCase: GetUserRepositoriesUseCase

    @Inject
    lateinit var saveRepositoryToDbUseCase: SaveRepositoryToDbUseCase

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

    fun saveToDb(gitRepo: GitRepo) {
        CoroutineScope(Dispatchers.IO).launch {
            saveRepositoryToDbUseCase.invoke(gitRepo)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}