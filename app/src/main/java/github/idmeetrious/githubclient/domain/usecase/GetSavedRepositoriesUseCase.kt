package github.idmeetrious.githubclient.domain.usecase

import github.idmeetrious.githubclient.domain.entities.GitRepo
import io.reactivex.rxjava3.core.Single

interface GetSavedRepositoriesUseCase {
    suspend fun invoke(): Single<List<GitRepo>>
}