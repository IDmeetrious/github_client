package github.idmeetrious.githubclient.domain.usecase

import github.idmeetrious.githubclient.domain.common.State

interface SaveRepositoryToFileUseCase {
    suspend fun invoke(data: ByteArray): State
}