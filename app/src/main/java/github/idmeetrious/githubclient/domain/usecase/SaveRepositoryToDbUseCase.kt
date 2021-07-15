package github.idmeetrious.githubclient.domain.usecase

import github.idmeetrious.githubclient.domain.entities.GitRepo

interface SaveRepositoryToDbUseCase {
    suspend fun invoke(gitRepo: GitRepo)
}