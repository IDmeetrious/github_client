package github.idmeetrious.githubclient.domain.usecase

import github.idmeetrious.githubclient.domain.common.State
import github.idmeetrious.githubclient.domain.entities.GitRepo
import github.idmeetrious.githubclient.domain.repositories.Repository

class SaveRepositoryToDbUseCaseImpl(
    private val repository: Repository
) : SaveRepositoryToDbUseCase {
    override suspend fun invoke(gitRepo: GitRepo) {
        return repository.saveRepositoryToDb(gitRepo)
    }
}