package github.idmeetrious.githubclient.domain.usecase

import github.idmeetrious.githubclient.domain.common.State
import github.idmeetrious.githubclient.domain.repositories.Repository

class SaveRepositoryToFileUseCaseImpl(
    private val repository: Repository
) : SaveRepositoryToFileUseCase {
    override suspend fun invoke(data: ByteArray): State {
        return repository.saveRepositoryToFile(data)
    }
}