package github.idmeetrious.githubclient.domain.usecase

import github.idmeetrious.githubclient.domain.repositories.Repository

class GetSavedRepositoriesUseCaseImpl(
    private val repository: Repository
): GetSavedRepositoriesUseCase {
    override fun invoke() = repository.getSavedRepositories()
}