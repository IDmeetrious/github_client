package github.idmeetrious.githubclient.domain.usecase

import github.idmeetrious.githubclient.domain.repositories.Repository
import io.reactivex.rxjava3.core.Single

class DownloadRepositoryUseCaseImpl(
    private val repository: Repository
) : DownloadRepositoryUseCase {
    override fun invoke(uri: String): Single<ByteArray> {
        return repository.downloadRepositoryZip(uri)
    }
}