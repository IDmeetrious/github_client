package github.idmeetrious.githubclient.domain.usecase

import github.idmeetrious.githubclient.domain.entities.GitRepo
import github.idmeetrious.githubclient.domain.repositories.Repository
import io.reactivex.rxjava3.core.Single

class GetUserRepositoriesUseCaseImpl(
    private val repository: Repository
) : GetUserRepositoriesUseCase {
    override suspend fun invoke(name: String): Single<List<GitRepo>> =
        repository.getUserRepositories(name)
}